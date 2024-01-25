package kz.abuova.FinalProjectOct.services;

import kz.abuova.FinalProjectOct.antities.Item;
import kz.abuova.FinalProjectOct.antities.Permission;
import kz.abuova.FinalProjectOct.antities.Users;
import kz.abuova.FinalProjectOct.repositories.PermissionRepository;
import kz.abuova.FinalProjectOct.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=usersRepository.findAllByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
    public String signUp(String name,String surname,String email,String password,String rePassword){
        String flag="userExist";
        Users checkUser=usersRepository.findAllByEmail(email);
        if(checkUser==null){
            flag="passwordNotMatch";
            if(password.equals(rePassword)){
                Permission permission=permissionRepository.findAllByRole("ROLE_CLIENT");
                List<Permission> permissions=new ArrayList<>();
                permissions.add(permission);
                Users user=new Users();
                user.setName(name);
                user.setSurname(surname);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(password));
                user.setPermissions(permissions);
                usersRepository.save(user);
                flag="SuccessRegister";
            }
        }
        return flag;
    }
    public String changeUserPassword(String email,String currentPassword,String newPassword,String retypePassword){
        String flag="userNotFound";
        Users user=usersRepository.findAllByEmail(email);
        if(user!=null){
            flag="passwordWrong";
            if(passwordEncoder.matches(currentPassword,user.getPassword())){
                flag="newPasswordNotMatches";
                if(newPassword.equals(retypePassword)){
                    flag="success";
                    user.setPassword(passwordEncoder.encode(newPassword));
                    usersRepository.save(user);
                }
            }
        }
        return flag;
    }
    public Users getUser(Long id){
        return usersRepository.findAllById(id);
    }
    public Users getUserEmail(String email){
        return usersRepository.findAllByEmail(email);
    }

}
