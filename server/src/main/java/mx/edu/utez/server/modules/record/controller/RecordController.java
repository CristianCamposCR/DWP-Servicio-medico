package mx.edu.utez.server.modules.record.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.record.service.RecordService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/record")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
}
