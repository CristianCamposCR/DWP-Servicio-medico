package mx.edu.utez.server.modules.user.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.*;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    public boolean isUserRelatedToAppointment(Long userId) {
        Instant currentDate = Instant.now();
        return this.iAppointmentRepository.existsByDoctorUserId(userId, currentDate) > 0 ||
                this.iAppointmentRepository.existsByPatientUserId(userId, currentDate) > 0;
    }
}