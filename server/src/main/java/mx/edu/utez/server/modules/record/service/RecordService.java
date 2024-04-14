package mx.edu.utez.server.modules.record.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.record.controller.dto.RecordDto;
import mx.edu.utez.server.modules.record.model.IRecordRepository;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    Logger logger = LoggerFactory.getLogger(RecordService.class);
    private final IRecordRepository iRecordRepository;
    private final IAppointmentRepository iAppointmentRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> save(RecordDto recordDto) {
        try {
            Appointment appointment = getAppointment(recordDto.getAppointment().getId());
            if (appointment == null)
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

            if (appointment.getStatus().getName() != Statuses.CONFIRMADA)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_IS_NOT_ACTIVE.name());

            iRecordRepository.saveAndFlush(recordDto.getRecordEntity());

            return new ResponseApi<>(
                    true,
                    HttpStatus.OK,
                    false,
                    "Registro médico realizado."
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
    public ResponseApi<Boolean> update(RecordDto recordDto) {
        try {
            Appointment appointment = getAppointment(recordDto.getAppointment().getId());
            if (appointment == null)
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_APPOINTMENT_FOUND.name());

            if (!iRecordRepository.existsById(recordDto.getId()))
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_RECORD_FOUND.name());

            if (appointment.getStatus().getName() != Statuses.CONFIRMADA)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.APPOINTMENT_IS_NOT_ACTIVE.name());

            iRecordRepository.saveAndFlush(recordDto.getRecordEntity());

            return new ResponseApi<>(
                    true,
                    HttpStatus.OK,
                    false,
                    "Registro médico actualizado."
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

    private Appointment getAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = iAppointmentRepository.findById(appointmentId);
        return optionalAppointment.orElse(null);
    }
}
