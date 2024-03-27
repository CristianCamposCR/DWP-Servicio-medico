package mx.edu.utez.server.modules.status.model;

import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByNameAndStatusType(Statuses name, StatusType statusType);
}
