package mx.edu.utez.server.modules.appointment_type.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment_type.model.AppointmentType;
import mx.edu.utez.server.modules.appointment_type.model.IAppointmentTypeRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentTypeService {
    private final IAppointmentTypeRepository iAppointmentTypeRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public AppointmentType findOrSave(String name, String description, Status status) {
        Optional<AppointmentType> optionalAppointmentType = this.iAppointmentTypeRepository.findByName(name);
        return optionalAppointmentType.orElseGet(() -> this.iAppointmentTypeRepository.saveAndFlush(new AppointmentType(name, description, status)));
    }

    @Transactional(readOnly = true)
    public ResponseApi<Set<AppointmentType>> openListAll() {
        try {
            Set<AppointmentType> appointmentTypes = this.iAppointmentTypeRepository.findAllByStatus_Name(Statuses.ACTIVO);
            return new ResponseApi<>(
                    appointmentTypes,
                    HttpStatus.OK,
                    false,
                    "Lista de tipos de cita."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }
}