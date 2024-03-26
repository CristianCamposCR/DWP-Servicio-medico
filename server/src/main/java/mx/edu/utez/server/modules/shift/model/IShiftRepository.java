package mx.edu.utez.server.modules.shift.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShiftRepository extends JpaRepository<Shift, Long> {
}