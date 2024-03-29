package mx.edu.utez.server.modules.doctor.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsBySpeciality_Id(Long id);
}
