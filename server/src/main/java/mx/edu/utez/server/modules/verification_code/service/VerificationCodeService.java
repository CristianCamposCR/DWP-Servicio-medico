package mx.edu.utez.server.modules.verification_code.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.email.controller.dto.EmailDto;
import mx.edu.utez.server.modules.email.service.EmailService;
import mx.edu.utez.server.modules.sms.controller.dto.SmsDto;
import mx.edu.utez.server.modules.sms.service.SmsService;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.modules.verification_code.controller.dto.GenerateVerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.controller.dto.VerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.model.IVerificationCodeRepository;
import mx.edu.utez.server.modules.verification_code.model.VerificationCode;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationCodeService {
    Logger logger = LoggerFactory.getLogger(VerificationCodeService.class);
    private final IVerificationCodeRepository iVerificationCodeRepository;
    private final IUserRepository iUserRepository;
    private final IStatusRepository iStatusRepository;
    private final HashService hashService;
    private final EmailService emailService;
    private final SmsService smsService;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public String generateVerificationCode(User user) {
        try {
            this.iVerificationCodeRepository.updateIsInvalidByUserId(user.getId());
            String originalCode = Methods.generateRandomString();
            String encryptedCode = hashService.encrypt(originalCode);
            this.iVerificationCodeRepository.saveAndFlush(new VerificationCode(
                    encryptedCode,
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
    public ResponseApi<Boolean> activateAccount(VerificationCodeDto verificationCodeDto) {
        try {
            String code = verificationCodeDto.getCode();
            String encryptedCode = this.hashService.encrypt(code);
            logger.info(encryptedCode);
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

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.ACTIVO, StatusType.USUARIOS);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_STATUS_FOUND.name());

            if (this.iUserRepository.updateStatusById(optionalStatus.get().getId(), verificationCode.getUser().getId()) == 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.ACCOUNT_NOT_ACTIVATED.name());

            verificationCode.setWasUsed(true);
            this.iVerificationCodeRepository.saveAndFlush(verificationCode);

            return new ResponseApi<>(true, HttpStatus.OK, false, "Cuenta activada.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
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

            return new ResponseApi<>(true, HttpStatus.OK, false, "Código verificado");
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> refreshCode(GenerateVerificationCodeDto dto, boolean isActivation) {
        try {
            Optional<User> optionalUser = this.iUserRepository.findByUsername(dto.getUsername());
            if (optionalUser.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

            User user = optionalUser.get();

            if (!isActivation) {
                // Obtener la zona horaria UTC
                ZoneId utcZoneId = ZoneId.of("UTC");

                // Convertir el instante actual a un LocalDate en UTC
                LocalDate currentDate = LocalDate.now(utcZoneId);

                // Obtener el inicio del día actual en UTC (00:00:00)
                Instant startOfDay = currentDate.atStartOfDay(utcZoneId).toInstant();

                // Obtener el fin del día actual en UTC (23:59:59.999999999)
                Instant endOfDay = currentDate.atTime(LocalTime.MAX).atZone(utcZoneId).toInstant();

                Long count = this.iVerificationCodeRepository.countByUser_IdAndCreatedAtBetween(user.getId(), startOfDay, endOfDay);

                if (count >= 5)
                    return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_CODES_REMAINING.name());
            }

            this.iVerificationCodeRepository.updateIsInvalidByUserId(user.getId());
            String originalCode = Methods.generateRandomString();
            String encryptedCode = hashService.encrypt(originalCode);
            this.iVerificationCodeRepository.saveAndFlush(new VerificationCode(
                    encryptedCode,
                    false,
                    false,
                    false,
                    user
            ));

            boolean sent;
            String body = "Este es tú código de verificación: " + originalCode;
            if (dto.getMode() == 1) {
                EmailDto emailDto = new EmailDto(
                        user.getPerson().getEmail(),
                        null,
                        "Confirmación de correo",
                        "Código de verificación",
                        body
                );
                sent = this.emailService.sendMail(emailDto);
            } else {
                SmsDto smsDto = new SmsDto(
                        user.getPerson().getPhoneNumber(),
                        body
                );
                sent = this.smsService.sendSms(smsDto);
            }

            if (!sent)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.ERROR_SENDING_CODE.name());

            return new ResponseApi<>(true, HttpStatus.OK, false, "Código enviado.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void updateExpiration() {
        this.iVerificationCodeRepository.updateIsExpiredByInstant(Instant.now());
    }
}