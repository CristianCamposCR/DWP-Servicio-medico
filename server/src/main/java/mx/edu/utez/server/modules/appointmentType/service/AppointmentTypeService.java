package mx.edu.utez.server.modules.appointmentType.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.appointmentType.model.AppointmentType;
import mx.edu.utez.server.modules.appointmentType.model.IAppointmentTypeRepository;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

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
}