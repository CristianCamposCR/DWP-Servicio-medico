package mx.edu.utez.server.modules.appointment.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment.controller.dto.RescheduleDto;
import mx.edu.utez.server.modules.appointment.controller.dto.SaveAppointmentDto;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.appointment_type.model.IAppointmentTypeRepository;
import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import mx.edu.utez.server.modules.email.controller.dto.EmailDto;
import mx.edu.utez.server.modules.email.service.EmailService;
import mx.edu.utez.server.modules.patient.model.IPatientRepository;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.payment.module.IPaymentRepository;
import mx.edu.utez.server.modules.payment.module.Payment;
import mx.edu.utez.server.modules.shift.model.IShiftRepository;
import mx.edu.utez.server.modules.speciality.model.ISpecialityRepository;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.SearchDto;
import mx.edu.utez.server.utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final IAppointmentRepository iAppointmentRepository;
    private final IPaymentRepository iPaymentRepository;
    private final IPatientRepository iPatientRepository;
    private final ISpecialityRepository iSpecialityRepository;
    private final IAppointmentTypeRepository iAppointmentTypeRepository;
    private final IShiftRepository iShiftRepository;
    private final IDoctorRepository iDoctorRepository;
    private final IStatusRepository iStatusRepository;
    private final IUserRepository iUserRepository;

    private final EmailService emailService;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> save(SaveAppointmentDto dto) throws MessagingException {
        Long patientId = dto.getPatient().getId();
        Long specialityId = dto.getSpeciality().getId();
        Long appointmentTypeId = dto.getAppointmentType().getId();
        Long shiftId = dto.getShift().getId();

        if (!patientExists(patientId))
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PATIENT_FOUND.name());

        if (!patientIsActive(patientId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.PATIENT_IS_INACTIVE.name());

        if (!specialityExists(specialityId))
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SPECIALITY_FOUND.name());

        if (!specialityIsActive(specialityId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SPECIALITY_IS_INACTIVE.name());

        if (!appointmentTypeExists(appointmentTypeId))
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_TYPE_FOUND.name());

        if (!appointmentTypeIsActive(appointmentTypeId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_TYPE_IS_INACTIVE.name());

        if (!shiftExists(shiftId))
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SHIFT_FOUND.name());

        if (!shiftIsActive(shiftId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SHIFT_IS_INACTIVE.name());

        if (!isAvailable(specialityId, shiftId, dto.getScheduledAt()))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_AVAILABILITY.name());

        Optional<Status> appointmentStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.AGENDADA, StatusType.CITAS);
        if (appointmentStatus.isEmpty())
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

        Optional<Status> paymentStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.PAGADO, StatusType.PAGOS);
        if (paymentStatus.isEmpty())
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

        Appointment appointment = dto.getAppointmentEntity();
        appointment.setStatus(appointmentStatus.get());
        appointment.setFolio(generateFolio(dto.getScheduledAt()));

        Appointment savedAppointment = this.iAppointmentRepository.saveAndFlush(appointment);

        Payment payment = dto.getPayment().getPaymentEntity();
        payment.setAppointment(savedAppointment);
        payment.setStatus(paymentStatus.get());

        Payment savedPayment = this.iPaymentRepository.saveAndFlush(payment);

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneId.of("America/Mexico_City"));

        String operationTime = formatter.format(savedPayment.getCreatedAt());

        Optional<Patient> optionalPatient = this.iPatientRepository.findById(dto.getPatient().getId());
        String body = "<p>Gracias por tu compra para la cita del díá: " + savedAppointment.getScheduledAt().toString() + "</p>\n" +
                "            <div class=\"ticket\">\n" +
                "                <p><strong>Folio de cita:</strong> " + savedAppointment.getFolio() + "</p>\n" +
                "                <p><strong>Total:</strong> " + savedPayment.getTotalPaid() + "</p>\n" +
                "                <p><strong>Fecha y hora de operación:</strong> " + operationTime + " </p>\n" +
                "            </div>";

        EmailDto emailDto = new EmailDto(
                optionalPatient.get().getPerson().getEmail(),
                null,
                "Compra realizada.",
                "¡Gracias por tu compra!",
                body
        );

        if (!this.emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_CODE.name());

        return new ResponseApi<>(
                true,
                HttpStatus.OK,
                false,
                "Cita creada."
        );
    }

    public boolean isAvailable(Long specialityId, Long shiftId, LocalDate scheduledAt) {
        Long availableAppointments = this.iDoctorRepository.checkAvailability(specialityId, shiftId);
        Long countAppointments = this.iAppointmentRepository.countByScheduledAtAndSpeciality_Id(scheduledAt, specialityId);
        long remainingAppointments = availableAppointments - countAppointments;
        logger.info(String.valueOf(remainingAppointments));
        return remainingAppointments > 0;
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllPendingByPatient(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllPendingByPatientAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllPendingByPatient(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas activas del paciente."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllActiveByPatient(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllActiveByPatientAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllActiveByPatient(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas activas del paciente."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllHistoryByPatient(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllHistoryByPatientAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllHistoryByPatient(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Historial de citas del paciente."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllAssignedByDoctor(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllAssignedByDoctorAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllAssignedByDoctor(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas asignadas al doctor."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllHistoryByDoctor(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllHistoryByDoctorAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllHistoryByDoctor(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Historial de citas del doctor."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllPending(SearchDto dto, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllPendingBySearchValue(dto.getSearchValue(), pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllPending(pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas pendientes."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllActive(SearchDto dto, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllActiveBySearchValue(dto.getSearchValue(), pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllActive(pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas activas."
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

    @Transactional(readOnly = true)
    public ResponseApi<Page<Appointment>> findAllHistory(SearchDto dto, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllHistoryBySearchValue(dto.getSearchValue(), pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllHistory(pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Historial de citas."
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
    public ResponseApi<Boolean> rescheduleAppointment(Long id, String username, RescheduleDto dto) throws MessagingException {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Appointment> optionalAppointment = this.iAppointmentRepository.findById(id);
            if (optionalAppointment.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, false, Errors.NO_APPOINTMENT_FOUND.name());

            Optional<Appointment> relatedAppointment = this.iAppointmentRepository.findByIdAndPatient_Person_User_Username(id, username);
            if (!Objects.equals(relatedAppointment.get().getId(), id))
                return new ResponseApi<>(HttpStatus.FORBIDDEN, true, Errors.CANNOT_ACCESS_TO_RESOURCE.name());

            Appointment existentAppointment = optionalAppointment.get();

            if (existentAppointment.getRemainingReschedules() <= 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_RESCHEDULES_REMAINING.name());

            if (!(existentAppointment.getStatus().getName() == Statuses.AGENDADA || existentAppointment.getStatus().getName() == Statuses.CONFIRMADA))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.CANNOT_RESCHEDULE.name());

            Long specialityId = existentAppointment.getSpeciality().getId();
            Long shiftId = dto.getShift().getId();

            if (!shiftExists(shiftId))
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_SHIFT_FOUND.name());

            if (!shiftIsActive(shiftId))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SHIFT_IS_INACTIVE.name());

            if (!isAvailable(specialityId, shiftId, dto.getScheduledAt()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_AVAILABILITY.name());

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.REAGENDADA, StatusType.CITAS);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            existentAppointment.setDoctor(null);
            existentAppointment.setStatus(optionalStatus.get());
            existentAppointment.setScheduledAt(dto.getScheduledAt());
            existentAppointment.setPreferentialShift(dto.getShift());
            existentAppointment.setRemainingReschedules(existentAppointment.getRemainingReschedules() - 1);

            Optional<User> optionalUser = this.iUserRepository.findByRole_Name(Roles.ADMIN);

            EmailDto emailDto = new EmailDto(
                    optionalUser.get().getPerson().getEmail(),
                    null,
                    "Solicitud de reagenda.",
                    "Reagenda de cita",
                    "Se ha solicitado una reagenda para la cita con el folio: <strong>" + existentAppointment.getFolio() + "</strong>"
            );

            if (!this.emailService.sendMail(emailDto))
                throw new MessagingException(Errors.ERROR_SENDING_CODE.name());

            return new ResponseApi<>(
                    true,
                    HttpStatus.OK,
                    false,
                    "Solicitud de reagenda realizada."
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

    private boolean patientExists(Long specialityId) {
        return this.iPatientRepository.existsById(specialityId);
    }

    private boolean patientIsActive(Long specialityId) {
        return this.iPatientRepository.existsByIdAndPerson_User_Status_Name(specialityId, Statuses.ACTIVO);
    }

    private boolean specialityExists(Long specialityId) {
        return this.iSpecialityRepository.existsById(specialityId);
    }

    private boolean specialityIsActive(Long specialityId) {
        return this.iSpecialityRepository.existsByIdAndStatus_Name(specialityId, Statuses.ACTIVO);
    }

    private boolean appointmentTypeExists(Long specialityId) {
        return this.iAppointmentTypeRepository.existsById(specialityId);
    }

    private boolean appointmentTypeIsActive(Long specialityId) {
        return this.iAppointmentTypeRepository.existsByIdAndStatus_Name(specialityId, Statuses.ACTIVO);
    }

    private boolean shiftExists(Long shiftId) {
        return this.iShiftRepository.existsById(shiftId);
    }

    private boolean shiftIsActive(Long shiftId) {
        return this.iShiftRepository.existsByIdAndStatus_Name(shiftId, Statuses.ACTIVO);
    }

    public String generateFolio(LocalDate scheduledAt) {
        Long appointmentsCount = this.iAppointmentRepository.count();
        String consecutiveId = String.format("%04d", appointmentsCount);
        String month = String.format("%02d", scheduledAt.getMonthValue());

        // Concatenar todos los componentes para formar el código
        return "F" + month + scheduledAt.getYear() + consecutiveId;
    }
}