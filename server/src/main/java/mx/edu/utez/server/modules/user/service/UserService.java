package mx.edu.utez.server.modules.user.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.email.controller.dto.EmailDto;
import mx.edu.utez.server.modules.email.service.EmailService;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.controller.dto.ChangePasswordDto;
import mx.edu.utez.server.modules.user.controller.dto.RecoveryPasswordDto;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository iUserRepository;
    private final IAppointmentRepository iAppointmentRepository;
    private final IStatusRepository iStatusRepository;
    private final PasswordEncoder passwordEncoder;
    private final HashService hashService;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<User> changeStatus(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<User> optionalUser = this.iUserRepository.findById(id);
            if (optionalUser.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

            if (isUserRelatedToAppointment(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.USER_HAS_DEPENDENCIES.name());

            Statuses newStatus = optionalUser.get().getStatus().getName().equals(Statuses.ACTIVO) ?
                    Statuses.INACTIVO : Statuses.ACTIVO;

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(newStatus, StatusType.USUARIOS);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            optionalUser.get().setStatus(optionalStatus.get());

            return new ResponseApi<>(
                    this.iUserRepository.saveAndFlush(optionalUser.get()),
                    HttpStatus.OK,
                    false,
                    "Estado de usuario actualizado."
            );
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
    public ResponseApi<Boolean> credentialsChangePassword(ChangePasswordDto dto, String username) throws MessagingException {
        User user = getUser(username);
        if (user == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

        String currentPassword = hashService.decrypt(dto.getCurrentPassword());
        String newPassword = hashService.decrypt(dto.getNewPassword());

        if (!passwordEncoder.matches(currentPassword, user.getPassword()))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INCORRECT_CURRENT_PASSWORD.name());

        if (currentPassword.equals(newPassword))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.PASSWORDS_ARE_THE_SANE.name());

        String newPasswordEncoded = passwordEncoder.encode(newPassword);

        user.setPassword(newPasswordEncoded);
        this.iUserRepository.saveAndFlush(user);

        EmailDto emailDto = new EmailDto(
                user.getPerson().getEmail(),
                Methods.getFullName(user.getPerson()),
                "Cambio de contraseña",
                "Cambio de contraseñá",
                "Tu contraseña ha sido actualizada correctamente."
        );

        if (!this.emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_EMAIL.name());

        return new ResponseApi<>(
                true,
                HttpStatus.OK,
                false,
                "Contraseña actualizasa."
        );
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> credentialsRecoveryPassword(RecoveryPasswordDto dto, String username) throws MessagingException {
        User user = getUser(username);
        if (user == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

        String newPassword = hashService.decrypt(dto.getNewPassword());

        if (passwordEncoder.matches(newPassword, user.getPassword()))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.PASSWORDS_ARE_THE_SANE.name());

        String newPasswordEncoded = passwordEncoder.encode(newPassword);

        user.setPassword(newPasswordEncoded);
        this.iUserRepository.saveAndFlush(user);

        EmailDto emailDto = new EmailDto(
                user.getPerson().getEmail(),
                Methods.getFullName(user.getPerson()),
                "Recuperación de contraseña",
                "Recuperación de contraseñá",
                "Tu contraseña se ha reestablecido correctamente."
        );

        if (!this.emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_EMAIL.name());

        return new ResponseApi<>(
                true,
                HttpStatus.OK,
                false,
                "Contraseña recuperada."
        );
    }

    private User getUser(String username) {
        Optional<User> optionalUser = this.iUserRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public boolean isUserRelatedToAppointment(Long userId) {
        Instant currentDate = Instant.now();
        return this.iAppointmentRepository.existsByDoctorUserId(userId, currentDate) > 0 ||
                this.iAppointmentRepository.existsByPatientUserId(userId, currentDate) > 0;
    }
}