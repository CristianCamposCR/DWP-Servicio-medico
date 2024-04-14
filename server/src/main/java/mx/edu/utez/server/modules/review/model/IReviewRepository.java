package mx.edu.utez.server.modules.review.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByDoctorPersonUserUsername(String username, Pageable pageable);
}
