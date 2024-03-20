package mx.edu.utez.server.modules.speciality.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialityRepository extends JpaRepository<Speciality, Long> {
}