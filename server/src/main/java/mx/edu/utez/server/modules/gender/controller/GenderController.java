package mx.edu.utez.server.modules.gender.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.gender.service.GenderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/gender")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class GenderController {
    private final GenderService genderService;
}