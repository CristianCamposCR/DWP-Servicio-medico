package mx.edu.utez.server.modules.email.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.email.controller.dto.EmailDto;
import mx.edu.utez.server.modules.email.service.EmailService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class EmailController {
    private final EmailService service;

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Object>> sendMail(@Valid @RequestBody EmailDto emailDto) {
        if (this.service.sendMail(emailDto)) {
            return new ResponseEntity<>(
                    new ResponseApi<>(
                            emailDto,
                            HttpStatus.OK,
                            false,
                            "Ok"
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new ResponseApi<>(
                        HttpStatus.BAD_REQUEST,
                        true,
                        Errors.REVIEW_REQUEST.name()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}