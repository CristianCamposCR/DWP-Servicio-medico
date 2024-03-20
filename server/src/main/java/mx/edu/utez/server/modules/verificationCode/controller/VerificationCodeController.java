package mx.edu.utez.server.modules.verificationCode.controller;

import mx.edu.utez.server.modules.verificationCode.service.VerificationCodeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/verification-code")
@CrossOrigin(origins = {"*"})
public class VerificationCodeController {
    private final VerificationCodeService verificationCodeService;

    public VerificationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }
}