package mx.edu.utez.server.modules.verification_code.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
}
