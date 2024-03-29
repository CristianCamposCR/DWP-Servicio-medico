package mx.edu.utez.server.modules.appointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsBySpeciality_Id(Long id);
}
