package mx.edu.utez.server.modules.gender.model;

import mx.edu.utez.server.kernel.Genders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByName(Genders name);
}
