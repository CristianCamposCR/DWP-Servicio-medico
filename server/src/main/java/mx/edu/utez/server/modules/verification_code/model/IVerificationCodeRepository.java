package mx.edu.utez.server.modules.verification_code.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface IVerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    @Modifying
    @Query(value = "UPDATE verification_codes SET is_invalid = 1 " +
            "WHERE user_id = ?1", nativeQuery = true)
    int updateIsInvalidByUserId(Long userId);

    @Modifying
    @Query(value = "UPDATE verification_codes SET is_expired = 1 " +
            "WHERE expire_at < ?1 and is_expired = 0", nativeQuery = true)
    int updateIsExpiredByInstant(Instant currentInstant);

    Long countByUser_IdAndCreatedAtBetween(Long userId, Instant firstDateTime, Instant secondDateTime);

    Optional<VerificationCode> findByCode(String code);
}
