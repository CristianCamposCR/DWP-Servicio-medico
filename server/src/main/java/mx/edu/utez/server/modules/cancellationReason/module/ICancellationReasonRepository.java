package mx.edu.utez.server.modules.cancellationReason.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICancellationReasonRepository extends JpaRepository<CancellationReason, Long> {
}
