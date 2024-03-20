package mx.edu.utez.server.modules.appointmentType.service;

import mx.edu.utez.server.modules.appointmentType.model.IAppointmentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppointmentTypeService {
    private final IAppointmentTypeRepository iAppointmentTypeRepository;

    public AppointmentTypeService(IAppointmentTypeRepository iAppointmentTypeRepository) {
        this.iAppointmentTypeRepository = iAppointmentTypeRepository;
    }
}