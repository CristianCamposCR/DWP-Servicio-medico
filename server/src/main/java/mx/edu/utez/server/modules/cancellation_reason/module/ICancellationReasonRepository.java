package mx.edu.utez.server.modules.cancellation_reason.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICancellationReasonRepository extends JpaRepository<CancellationReason, Long> {
    Optional<CancellationReason> findByReason(String reason);
}