package mx.edu.utez.server.modules.verification_code.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.MessageType;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.modules.verification_code.controller.dto.VerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.model.IVerificationCodeRepository;
import mx.edu.utez.server.modules.verification_code.model.VerificationCode;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationCodeService {
    private final IVerificationCodeRepository iVerificationCodeRepository;
    private final HashService hashService;

    @Transactional(readOnly = true)
    public String generateVerificationCode(User user, MessageType messageType) {
        try {
            this.iVerificationCodeRepository.updateIsInvalidByUserId(user.getId());
            String originalCode = Methods.generateRandomString();
            String encryptedCode = hashService.encrypt(originalCode);
            this.iVerificationCodeRepository.saveAndFlush(new VerificationCode(
                    encryptedCode,
                    messageType,
                    false,
                    false,
                    false,
                    user
            ));

            return originalCode;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> verifyCode(VerificationCodeDto verificationCodeDto) {
        try {
            String code = verificationCodeDto.getCode();
            String encryptedCode = this.hashService.encrypt(code);
            Optional<VerificationCode> optionalVerificationCode = this.iVerificationCodeRepository.findByCode(encryptedCode);
            if (optionalVerificationCode.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_VERIFICATION_CODE_FOUND.name());

            VerificationCode verificationCode = optionalVerificationCode.get();
            if (verificationCode.getWasUsed())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.VERIFICATION_CODE_WAS_ALREADY_USED.name());

            if (verificationCode.getIsInvalid())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.VERIFICATION_CODE_IS_INVALID.name());

            if (verificationCode.getIsExpired())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.VERIFICATION_CODE_IS_EXPIRED.name());

            verificationCode.setWasUsed(true);
            this.iVerificationCodeRepository.saveAndFlush(verificationCode);

            return new ResponseApi<>(true, HttpStatus.ACCEPTED, false, "Código verificado");
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void updateExpiration() {
        this.iVerificationCodeRepository.updateIsExpiredByInstant(Instant.now());
    }
}