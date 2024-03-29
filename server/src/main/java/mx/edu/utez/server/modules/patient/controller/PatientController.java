package mx.edu.utez.server.modules.patient.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.patient.service.PatientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/patient")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
}