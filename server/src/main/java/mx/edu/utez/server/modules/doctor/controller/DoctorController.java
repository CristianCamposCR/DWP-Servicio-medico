package mx.edu.utez.server.modules.doctor.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/doctor")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
}