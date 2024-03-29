package mx.edu.utez.server.modules.appointment.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final IAppointmentRepository iAppointmentRepository;
}