package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PhotoRepository extends JpaRepository<Photo,Long> {

}
