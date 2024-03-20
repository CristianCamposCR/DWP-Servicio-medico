package mx.edu.utez.server.modules.area.controller;

import mx.edu.utez.server.modules.area.service.AreaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/area")
@CrossOrigin(origins = {"*"})
public class AreaController {
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }
}