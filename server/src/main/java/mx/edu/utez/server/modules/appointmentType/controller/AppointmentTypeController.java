package mx.edu.utez.server.modules.appointmentType.controller;

import mx.edu.utez.server.modules.appointmentType.service.AppointmentTypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/appointment-type")
@CrossOrigin(origins = {"*"})
public class AppointmentTypeController {
    private final AppointmentTypeService appointmentTypeService;

    public AppointmentTypeController(AppointmentTypeService appointmentTypeService) {
        this.appointmentTypeService = appointmentTypeService;
    }
}