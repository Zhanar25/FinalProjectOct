package kz.abuova.FinalProjectOct.repositories;

import kz.abuova.FinalProjectOct.antities.Item;
import kz.abuova.FinalProjectOct.antities.UserItem;
import kz.abuova.FinalProjectOct.antities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersItemRepository extends JpaRepository<UserItem,Long> {
    List<UserItem> findByUser(Users user);
    UserItem findByUserAndItem(Users users, Item item);
}
