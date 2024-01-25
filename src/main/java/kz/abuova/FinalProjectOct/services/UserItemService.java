package kz.abuova.FinalProjectOct.services;

import kz.abuova.FinalProjectOct.antities.Item;
import kz.abuova.FinalProjectOct.antities.UserItem;
import kz.abuova.FinalProjectOct.antities.Users;
import kz.abuova.FinalProjectOct.repositories.UsersItemRepository;
import kz.abuova.FinalProjectOct.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserItemService {
    @Autowired
    private UsersItemRepository usersItemRepository;

    public List<UserItem> getUserItems(Users user) {
        return usersItemRepository.findByUser(user);
    }

    public void updateCartItemQuantity(Users users, Item item, int quantity) {
        UserItem userItem = usersItemRepository.findByUserAndItem(users, item);
        if (userItem != null) {
            userItem.setQuantity(quantity);
            usersItemRepository.save(userItem);
        }
    }}
