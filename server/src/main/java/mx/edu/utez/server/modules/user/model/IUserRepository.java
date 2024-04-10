package mx.edu.utez.server.modules.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsernameIgnoreCase(String username);

    @Modifying
    @Query(value = """
                UPDATE users SET status_id = ?1 WHERE id = ?2
            """, nativeQuery = true)
    int updateStatusById(Long statusId, Long userId);
}
