package mx.edu.utez.server.modules.appointmentType.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IAppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    Optional<AppointmentType> findByName(String name);

    Set<AppointmentType> findAllByStatus_Name(Statuses statusName);
}
