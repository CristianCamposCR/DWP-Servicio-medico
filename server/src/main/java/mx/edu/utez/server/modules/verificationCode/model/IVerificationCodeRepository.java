package mx.edu.utez.server.modules.verificationCode.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
}
