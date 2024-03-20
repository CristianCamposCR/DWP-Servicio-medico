package mx.edu.utez.server.modules.gender.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Long> {
}
