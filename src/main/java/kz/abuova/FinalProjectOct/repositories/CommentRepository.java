package kz.abuova.FinalProjectOct.repositories;

import jakarta.transaction.Transactional;
import kz.abuova.FinalProjectOct.antities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findAllById(Long id);
}
