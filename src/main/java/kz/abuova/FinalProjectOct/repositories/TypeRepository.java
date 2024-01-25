package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.TypeOfMeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TypeRepository extends JpaRepository<TypeOfMeat,Long> {
    TypeOfMeat findAllById(Long id);
}
