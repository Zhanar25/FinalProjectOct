package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Item;
import kz.abuova.FinalProjectOct.antities.Users;
import kz.abuova.FinalProjectOct.antities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface VoteRepository extends JpaRepository<Vote,Long> {
    List<Vote> findByItem(Item item);

}
