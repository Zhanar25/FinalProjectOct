package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findAllById(Long id);
}
