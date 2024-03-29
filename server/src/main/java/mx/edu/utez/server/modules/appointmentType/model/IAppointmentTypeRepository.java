package mx.edu.utez.server.modules.appointmentType.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    Optional<AppointmentType> findByName(String name);
}
