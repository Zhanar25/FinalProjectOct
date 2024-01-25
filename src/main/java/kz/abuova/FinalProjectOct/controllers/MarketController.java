package kz.abuova.FinalProjectOct.controllers;

import kz.abuova.FinalProjectOct.antities.*;
import kz.abuova.FinalProjectOct.repositories.*;
import kz.abuova.FinalProjectOct.services.MyUserService;
import kz.abuova.FinalProjectOct.services.PhotoService;
import kz.abuova.FinalProjectOct.services.UserItemService;
import kz.abuova.FinalProjectOct.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/market")

public class MarketController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserItemService userItemService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UsersItemRepository usersItemRepository;

    @GetMapping(value = "/home")
    @PreAuthorize("isAuthenticated()")
    public String homePage(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Ingredients> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        List<Users> users = usersRepository.findAll();
        model.addAttribute("users", users);
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        List<Vote> votes = voteRepository.findAll();
        model.addAttribute("votes", votes);


        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/change-password")
    public String changePasswordOpen() {
        return "change-password";
    }

    @PostMapping(value = "/change-password")
    public String changePasswordPost(@RequestParam("current-email") String email,
                                     @RequestParam("current-password") String currentPassword,
                                     @RequestParam("new-password") String newPassword,
                                     @RequestParam("retype-password") String retypePassword) {

        String checkFlag = myUserService.changeUserPassword(email, currentPassword, newPassword, retypePassword);
        if (checkFlag.equals("userNotFound")) {
            return "redirect:change-password?userNotFound";
        } else if (checkFlag.equals("passwordWrong")) {
            return "redirect:change-password?passwordWrong";
        } else if (checkFlag.equals("newPasswordNotMatches")) {
            return "redirect:change-password?newPasswordNotMatches";
        } else {
            return "redirect:change-password?success";
        }
    }

    @GetMapping(value = "/403")
    public String openAccess() {
        return "403";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/login")
    public String openLogin() {
        return "login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public String openRegister() {
        return "register";
    }


    @PostMapping(value = "/sign-up")
    public String signUpUser(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("re-password") String rePassword) {
        String checkFlag = myUserService.signUp(name, surname, email, password, rePassword);
        if (checkFlag.equals("userExist")) {
            return "redirect:register?userExist";
        } else if (checkFlag.equals("passwordNotMatch")) {
            return "redirect:register?passwordNotMatch";
        } else {
            return "redirect:register?SuccessRegister";
        }
    }

    @GetMapping(value = "/item-search")
    public String searchedItem(@RequestParam("search") String search, Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        List<Item> items = null;
        for (int i = 0; i < search.length(); i++) {
            if (Character.isDigit(search.charAt(i))) {
                int numericValue = Integer.parseInt(search);
                items = itemRepository.findAllByAmountEqualsOrPriceEquals(numericValue, numericValue);
            } else {
                items = itemRepository.findAllByNameContainsIgnoreCaseOrCategoriesNameContainingIgnoreCaseOrCompaniesNameContainingIgnoreCase(search, search, search);
            }
        }
        model.addAttribute("items", items);
        return "home";
    }

    @PostMapping(value = "/add-item")
    public String addItemPost(@RequestParam("item-name") String name,
                              @RequestParam("item-amount") int amount,
                              @RequestParam("item-price") int price,
                              @RequestParam("category-id") Long categoryId,
                              @RequestParam("company-id") Long companyId,
                              @RequestParam("ingredients") List<Ingredients> ingredients) {
        Category category = categoryRepository.findAllById(categoryId);
        category.setIngredientsList(ingredients);
        categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Company company = companyRepository.findAllById(companyId);
        companyRepository.save(company);
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        Item item = Item.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .categories(categories)
                .companies(companies)
                .build();
        itemRepository.save(item);
        return "redirect:home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/shops/{id}")
    public String shopsOpen(@PathVariable("id") Long companyId, Model model) {
        List<Item> items = itemRepository.findByCompaniesId(companyId);
        model.addAttribute("items", items);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Ingredients> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "shops";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/basket/{id}")
    public String usersBasket(@PathVariable("id") Long userId, Model model) {
        List<Item> items = itemRepository.findByUsersId(userId);
        model.addAttribute("items", items);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "basket";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/details/{id}")
    public String detailsOpen(@PathVariable("id") Long itemId, Model model) {
        Item item = itemRepository.findAllById(itemId);
        model.addAttribute("item", item);
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Ingredients> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "details";
    }

    @PostMapping(value = "/update-item")
    public String updateItem(Item updateItem,
                             @RequestParam("category-id") Long categoryId,
                             @RequestParam("company-id") Long companyId,
                             @RequestParam("ingredient-id") List<Ingredients> ingredients) {
        Category category = categoryRepository.findAllById(categoryId);
        category.setIngredientsList(ingredients);
        categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Company company = companyRepository.findAllById(companyId);
        companyRepository.save(company);
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        Item item = itemRepository.findAllById(updateItem.getId());
        item.setName(updateItem.getName());
        item.setPrice(updateItem.getPrice());
        item.setAmount(updateItem.getAmount());
        item.setCategories(categories);
        item.setCompanies(companies);
        itemRepository.save(item);
        return "redirect:details/" + item.getId();
    }

    @PostMapping(value = "/delete")
    public String deleteItemPost(@RequestParam("item-id") Long id) {
        itemRepository.deleteById(id);
        return "redirect:home";
    }


    @PostMapping(value = "/add-basket-user")
    public String addItemToUser(@RequestParam("user-id") Long userId, @RequestParam("item-id") Long itemId) {
        Users users = usersRepository.findAllById(userId);
        Item item = itemRepository.findAllById(itemId);

        List<Users> users1 = item.getUsers();
        if (!users1.contains(users)) {
            users1.add(users);
            item.setUsers(users1);
            itemRepository.save(item);
        }
        return "redirect:home";
    }

    @PostMapping(value = "/updateUsersItem")
    public String updateCartItem(@RequestParam Long userId, @RequestParam Long itemId, @RequestParam int quantity) {
        Users users = usersRepository.findAllById(userId);
        Item item = itemRepository.findAllById(itemId);

        userItemService.updateCartItemQuantity(users, item, quantity);

        return "redirect:home";
    }

    @PostMapping(value = "/upload-photo")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file, @RequestParam("email") String email) {
        photoService.uploadPhoto(file, email);
        Users users = myUserService.getUserEmail(email);
        return "redirect:profile/" + users.getId();
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin-panel")
    public String adminOpen(Model model) {
        List<Users> users = usersRepository.findAll();
        model.addAttribute("users", users);
        return "admin-panel";
    }

    @PostMapping(value = "/delete-user")
    public String deleteUserPost(@RequestParam("user-id") Long id) {
        usersRepository.deleteById(id);
        return "redirect:admin-panel";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile/{id}")
    public String profileOpen(@PathVariable("id") Long id, Model model) {
        model.addAttribute("currentUser", myUserService.getUser(id));
        List<UserItem> userItems = usersItemRepository.findAll();
        model.addAttribute("userItems", userItems);
        return "profile";
    }

    @PostMapping(value = "/information")
    public String myInformation(Users newUser) {
        Users users = usersRepository.findAllById(newUser.getId());
        users.setName(newUser.getName());
        users.setSurname(newUser.getSurname());
        users.setEmail(newUser.getEmail());
        usersRepository.save(users);
        return "redirect:home";

    }

    @PostMapping(value = "/comment")
    public String addComment(@RequestParam("user-id") Long userId, @RequestParam("item-id") Long itemId,
                             @RequestParam("comment") String comment) {
        Item item = itemRepository.findAllById(itemId);
        Users users = usersRepository.findAllById(userId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comments = Comment.builder()
                .comment(comment)
                .date(timestamp)
                .item(item)
                .users(users)
                .build();
        commentRepository.save(comments);
        item.getComments().add(comments);
        users.getComments().add(comments);
        usersRepository.save(users);
        itemRepository.save(item);
        return "redirect:home";
    }


    @PostMapping(value = "/vote")
    public String voteItem(@RequestParam("user-id") Long userId, @RequestParam("item-id") Long itemId,
                           @RequestParam("rating") int rating) {
        Item item = itemRepository.findAllById(itemId);
        Users users = usersRepository.findAllById(userId);
        Vote vote = Vote.builder()
                .user(users)
                .item(item)
                .upvote(rating)
                .build();
            voteRepository.save(vote);

        return "redirect:home";

    }

}