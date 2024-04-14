package mx.edu.utez.server.modules.appointment.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.CancellationReasons;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment.controller.dto.AssignDto;
import mx.edu.utez.server.modules.appointment.controller.dto.RescheduleDto;
import mx.edu.utez.server.modules.appointment.controller.dto.SaveAppointmentDto;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.appointment_type.model.IAppointmentTypeRepository;
import mx.edu.utez.server.modules.cancellation_reason.module.CancellationReason;
import mx.edu.utez.server.modules.cancellation_reason.module.ICancellationReasonRepository;
import mx.edu.utez.server.modules.doctor.model.Doctor;
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
import mx.edu.utez.server.utils.Methods;
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
    private final ICancellationReasonRepository iCancellationReasonRepository;

    private final EmailService emailService;
    final String DATE_TEXT_FORMAT = "EEEE dd 'de' MMMM 'del' yyyy";

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> save(SaveAppointmentDto dto, String username) throws MessagingException {
        Long specialityId = dto.getSpeciality().getId();
        Long appointmentTypeId = dto.getAppointmentType().getId();
        Long shiftId = dto.getShift().getId();

        Patient patient = getPatientByUsername(username, Statuses.INACTIVO);
        if (patient == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PATIENT_FOUND.name());

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
        appointment.setPatient(patient);

        Appointment savedAppointment = this.iAppointmentRepository.saveAndFlush(appointment);

        Payment payment = dto.getPayment().getPaymentEntity();
        payment.setAppointment(savedAppointment);
        payment.setStatus(paymentStatus.get());

        Payment savedPayment = this.iPaymentRepository.saveAndFlush(payment);

        String body = genCreationAppointmentEmailBody(savedAppointment, savedPayment);

        EmailDto emailDto = new EmailDto(
                patient.getPerson().getEmail(),
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
        String dayName = scheduledAt.getDayOfWeek().toString();
        Long availableAppointments = this.iDoctorRepository.checkAvailability(specialityId, shiftId, dayName);
        Long countAppointments = this.iAppointmentRepository.countByScheduledAtAndSpeciality_Id(scheduledAt, specialityId);
        long remainingAppointments = availableAppointments - countAppointments;
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
    public ResponseApi<Page<Appointment>> findAllToReviewByPatient(SearchDto dto, String username, Pageable pageable) {
        try {
            Page<Appointment> appointments;
            if (dto != null && dto.getSearchValue() != null && !dto.getSearchValue().isBlank()) {
                appointments = this.iAppointmentRepository.findAllToReviewByPatientAndSearchValue(dto.getSearchValue(), username, pageable);
            } else {
                appointments = this.iAppointmentRepository.findAllToReviewByPatient(username, pageable);
            }

            return new ResponseApi<>(
                    appointments,
                    HttpStatus.OK,
                    false,
                    "Citas sin reseña."
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
    public ResponseApi<Boolean> updateToRescheduleAppointment(Long id, String username, RescheduleDto dto) throws MessagingException {
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

        User user = getAdmin();
        if (user == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

        EmailDto emailDto = new EmailDto(
                user.getPerson().getEmail(),
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
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> updateToConfirmAppointment(AssignDto dto, Long appointmentId, boolean isAssignment) throws MessagingException {
        if (Validations.isInvalidId(appointmentId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

        Appointment appointment = getAppointment(appointmentId);
        if (appointment == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

        if (isAssignment && appointment.getStatus().getName() == Statuses.CONFIRMADA)
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_WAS_ALREADY_CONFIRMED.name());

        if (isAssignment && appointment.getStatus().getName() != Statuses.AGENDADA && appointment.getStatus().getName() != Statuses.REAGENDADA)
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_IS_NOT_PENDING.name());

        Doctor doctor = getDoctor(dto.getDoctor().getId());
        if (doctor == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_DOCTOR_FOUND.name());

        Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.CONFIRMADA, StatusType.CITAS);
        if (optionalStatus.isEmpty())
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

        appointment.setStatus(optionalStatus.get());
        appointment.setDoctor(doctor);
        appointment.setScheduledHour(dto.getScheduledHour());

        String fullName = Methods.getFullName(appointment.getPatient().getPerson());
        String body = genConfirmationAppointmentEmailBody(appointment);
        EmailDto emailDto = new EmailDto(
                appointment.getPatient().getPerson().getEmail(),
                fullName,
                isAssignment ? "Confirmación de cita." : "Reasignación de cita.",
                isAssignment ? "¡Tu cita fue confirmada!" : "¡Tu cita fue reasignada!",
                body);

        if (!emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_CODE.name());

        iAppointmentRepository.saveAndFlush(appointment);
        return new ResponseApi<>(true, HttpStatus.OK, false, "Cita confirmada.");
    }

    @Transactional(readOnly = true)
    public ResponseApi<Boolean> updateToNotifyNonAvailability(Long appointmentId, String username) throws MessagingException {
        if (Validations.isInvalidId(appointmentId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

        Appointment appointment = getAppointment(appointmentId);
        if (appointment == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

        if (appointment.getStatus().getName() != Statuses.CONFIRMADA)
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_IS_NOT_ACTIVE.name());

        if (!appointment.getDoctor().getPerson().getUser().getUsername().equals(username))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.CANNOT_ACCESS_TO_RESOURCE.name());

        User user = getAdmin();
        if (user == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_USER_FOUND.name());

        String body = genNonAvAppEmailBody(appointment);
        EmailDto emailDto = new EmailDto(
                user.getPerson().getEmail(),
                null,
                "Doctor no disponible",
                "Notificación de no disponibilidad",
                body
        );

        if (!this.emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_CODE.name());

        return new ResponseApi<>(true, HttpStatus.OK, false, "Notificación enviada.");
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class, MessagingException.class})
    public ResponseApi<Boolean> updateToCancelAppointment(Long appointmentId, CancellationReasons reason) throws MessagingException {
        if (Validations.isInvalidId(appointmentId))
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

        Appointment appointment = getAppointment(appointmentId);
        if (appointment == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

        CancellationReason cancellationReason = getCancellationReason(reason);
        if (cancellationReason == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_CANCELLATION_REASON_FOUND.name());

        Status appointmentStatus = getStatus(Statuses.CANCELADA, StatusType.CITAS);
        if (appointmentStatus == null)
            return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

        appointment.setCancellationReason(cancellationReason);
        appointment.setStatus(appointmentStatus);

        if (cancellationReason.getRefundPercent() > 0) {
            Payment payment = appointment.getPayment();
            Status paymentStatus = getStatus(Statuses.REEMBOLSADO, StatusType.PAGOS);
            if (paymentStatus == null)
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            Double refundedAmount = payment.getTotalPaid() * cancellationReason.getRefundPercent() / 100;
            payment.setRefundedAmount(refundedAmount);
            payment.setStatus(paymentStatus);
            this.iPaymentRepository.saveAndFlush(payment);
        }

        String patientFullName = Methods.getFullName(appointment.getPatient().getPerson());
        String body = genCancellationAppEmailBody(appointment, getCancellationReasonMessage(cancellationReason.getReason()));
        EmailDto emailDto = new EmailDto(
                appointment.getPatient().getPerson().getEmail(),
                patientFullName,
                "Cita cancelata",
                "Tu cita ha sido cancelada",
                body
        );

        if (!this.emailService.sendMail(emailDto))
            throw new MessagingException(Errors.ERROR_SENDING_CODE.name());

        return new ResponseApi<>(true, HttpStatus.OK, false, "Notificación de cancelación enviada.");
    }

    @Transactional(readOnly = true)
    public ResponseApi<Appointment> findOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Appointment> optionalAppointment = iAppointmentRepository.findById(id);
            return optionalAppointment.map(appointment ->
                    new ResponseApi<>(appointment, HttpStatus.OK, false, "Cita")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name())
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

    private String genConfirmationAppointmentEmailBody(Appointment appointment) {
        String fullName = Methods.getFullName(appointment.getDoctor().getPerson());
        String formattedScheduledAt = Methods.formatScheduledAt(appointment.getScheduledAt(), DATE_TEXT_FORMAT);

        return "<p>Se ha confirmado tu cita para el díá: " + formattedScheduledAt + "</p>\n" +
                "            <div class=\"ticket\">\n" +
                "                <p><strong>Tipo de cita:</strong> " + appointment.getAppointmentType().getName() + "</p>\n" +
                "                <p><strong>Doctor que atenderá:</strong> " + fullName + "</p>\n" +
                "                <p><strong>Especialidad:</strong> " + appointment.getDoctor().getSpeciality().getName() + "</p>\n" +
                "                <p><strong>Hora:</strong> " + appointment.getScheduledHour() + ":00 HRS </p>\n" +
                "            </div>";
    }

    private String genCreationAppointmentEmailBody(Appointment appointment, Payment payment) {
        String formattedScheduledAt = Methods.formatScheduledAt(appointment.getScheduledAt(), DATE_TEXT_FORMAT);
        String operationTime = Methods.formatLocalDateTime(payment.getCreatedAt(), "dd-MM-yyyy HH:mm", "America/Mexico_City");

        return "<p>Gracias por tu compra para la cita del díá: " + formattedScheduledAt + "</p>\n" +
                "            <div class=\"ticket\">\n" +
                "                <p><strong>Folio de cita:</strong> " + appointment.getFolio() + "</p>\n" +
                "                <p><strong>Total:</strong> " + payment.getTotalPaid() + "</p>\n" +
                "                <p><strong>Fecha y hora de operación:</strong> " + operationTime + " </p>\n" +
                "            </div>";
    }

    private String genNonAvAppEmailBody(Appointment appointment) {
        String fullName = Methods.getFullName(appointment.getDoctor().getPerson());
        String formattedScheduledAt = Methods.formatScheduledAt(appointment.getScheduledAt(), DATE_TEXT_FORMAT);

        return "<p>El doctor ha notificado que no estará disponible para la cita.</p>\n" +
                "            <div class=\"ticket\">\n" +
                "                <p><strong>Folio de cita: </strong> " + appointment.getFolio() + "</p>\n" +
                "                <p><strong>Tipo de cita: </strong> " + appointment.getAppointmentType().getName() + "</p>\n" +
                "                <p><strong>Doctor asignado: </strong> " + fullName + "</p>\n" +
                "                <p><strong>Especialidad: </strong> " + appointment.getDoctor().getSpeciality().getName() + "</p>\n" +
                "                <p><strong>Fecha: </strong> " + formattedScheduledAt + "</p>\n" +
                "                <p><strong>Hora: </strong> " + appointment.getScheduledHour() + ":00 HRS </p>\n" +
                "            </div>";
    }

    private String genCancellationAppEmailBody(Appointment appointment, String reason) {
        String doctorFullName = Methods.getFullName(appointment.getDoctor().getPerson());
        String formattedScheduledAt = Methods.formatScheduledAt(appointment.getScheduledAt(), DATE_TEXT_FORMAT);

        return "<p>Información de la cita cancelada.</p>\n" +
                "            <div class=\"ticket\">\n" +
                "                <p><strong>Razón de cancelación: </strong> " + reason + "</p>\n" +
                "                <p><strong>Tipo de cita: </strong> " + appointment.getAppointmentType().getName() + "</p>\n" +
                "                <p><strong>Folio de cita: </strong> " + appointment.getFolio() + "</p>\n" +
                "                <p><strong>Doctor asignado: </strong> " + doctorFullName + "</p>\n" +
                "                <p><strong>Especialidad: </strong> " + appointment.getDoctor().getSpeciality().getName() + "</p>\n" +
                "                <p><strong>Fecha: </strong> " + formattedScheduledAt + "</p>\n" +
                "                <p><strong>Hora: </strong> " + appointment.getScheduledHour() + ":00 HRS </p>\n" +
                "            </div>";
    }

    private Doctor getDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = iDoctorRepository.findByIdAndPerson_User_StatusNameNot(doctorId, Statuses.INACTIVO);
        return optionalDoctor.orElse(null);
    }

    private Appointment getAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = iAppointmentRepository.findById(appointmentId);
        return optionalAppointment.orElse(null);
    }

    private User getAdmin() {
        Optional<User> optionalUser = this.iUserRepository.findByRole_Name(Roles.ADMIN);
        return optionalUser.orElse(null);
    }

    private CancellationReason getCancellationReason(CancellationReasons cancellationReason) {
        Optional<CancellationReason> optionalCancellationReason = this.iCancellationReasonRepository.findByReason(cancellationReason);
        return optionalCancellationReason.orElse(null);
    }

    private Status getStatus(Statuses statusName, StatusType statusType) {
        Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(statusName, statusType);
        return optionalStatus.orElse(null);
    }

    private Patient getPatientByUsername(String username, Statuses statusName) {
        Optional<Patient> optionalPatient = this.iPatientRepository.findByPerson_User_UsernameAndPerson_User_Status_Name_Not(username, statusName);
        return optionalPatient.orElse(null);
    }

    private String getCancellationReasonMessage(CancellationReasons reason) {
        return switch (reason.ordinal()) {
            case 0 -> "Sin doctores disponibles.";
            case 1 -> "Cita no atendida.";
            case 2 -> "Paciente ausente.";
            case 3 -> "Cancelación por paciente.";
            default -> "No espcificada";
        };
    }

    private boolean patientExists(Long patientId) {
        return this.iPatientRepository.existsById(patientId);
    }

    private boolean patientIsActive(Long patientId) {
        return this.iPatientRepository.existsByIdAndPerson_User_Status_Name(patientId, Statuses.ACTIVO);
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