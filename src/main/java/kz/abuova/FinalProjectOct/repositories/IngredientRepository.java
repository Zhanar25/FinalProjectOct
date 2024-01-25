package kz.abuova.FinalProjectOct.repositories;

import kz.abuova.FinalProjectOct.antities.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IngredientRepository extends JpaRepository<Ingredients,Long> {
    Ingredients findAllById(Long id);


}
