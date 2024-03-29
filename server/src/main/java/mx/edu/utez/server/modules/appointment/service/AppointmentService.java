package mx.edu.utez.server.modules.appointment.service;

import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppointmentService {
    private final IAppointmentRepository iAppointmentRepository;

    public AppointmentService(IAppointmentRepository iAppointmentRepository) {
        this.iAppointmentRepository = iAppointmentRepository;
    }
}