package mx.edu.utez.server.modules.shift.model;

import mx.edu.utez.server.kernel.Shifts;
import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IShiftRepository extends JpaRepository<Shift, Long> {
    Optional<Shift> findByName(Shifts name);

    Set<Shift> findAllByStatus_Name(Statuses statusName);

    boolean existsByIdAndStatus_Name(Long id, Statuses statusName);

}