package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findAllById(Long id);
    List<Item>findAllByOrderByNameDesc();
    List<Item>findAllByOrderByNameAsc();
    List<Item> findAllByAmountEqualsOrPriceEquals(int price, int amount);
    List<Item> findAllByNameContainsIgnoreCaseOrCategoriesNameContainingIgnoreCaseOrCompaniesNameContainingIgnoreCase(String name,String company,String category);
    List<Item> findByCompaniesId(Long companyId);
    List<Item>findByUsersId(Long userId);
}
