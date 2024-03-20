package mx.edu.utez.server.modules.verificationCode.service;

import mx.edu.utez.server.modules.verificationCode.model.IVerificationCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationCodeService {
    private final IVerificationCodeRepository iVerificationCodeRepository;

    public VerificationCodeService(IVerificationCodeRepository iVerificationCodeRepository) {
        this.iVerificationCodeRepository = iVerificationCodeRepository;
    }
}