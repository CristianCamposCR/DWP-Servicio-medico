package mx.edu.utez.server.modules.appointment.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.appointment.service.AppointmentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/appointment")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
}