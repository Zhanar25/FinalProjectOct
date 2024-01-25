package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findAllByEmail(String email);
    Users findAllById(Long id);
}
