package mx.edu.utez.server.modules.gender.model;

import mx.edu.utez.server.kernel.Genders;
import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByName(Genders name);

    Set<Gender> findAllByStatus_Name(Statuses statusName);
}
