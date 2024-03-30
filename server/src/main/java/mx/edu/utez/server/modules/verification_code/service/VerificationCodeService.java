package mx.edu.utez.server.modules.verification_code.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.verification_code.model.IVerificationCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationCodeService {
    private final IVerificationCodeRepository iVerificationCodeRepository;
}