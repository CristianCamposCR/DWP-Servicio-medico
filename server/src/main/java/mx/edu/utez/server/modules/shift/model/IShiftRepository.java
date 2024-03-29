package mx.edu.utez.server.modules.shift.model;

import mx.edu.utez.server.kernel.Shifts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IShiftRepository extends JpaRepository<Shift, Long> {
    Optional<Shift> findByName(Shifts name);
}