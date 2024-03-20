package mx.edu.utez.server.modules.speciality.controller;

import mx.edu.utez.server.modules.speciality.service.SpecialityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/speciality")
@CrossOrigin(origins = {"*"})
public class SpecialityController {
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
}