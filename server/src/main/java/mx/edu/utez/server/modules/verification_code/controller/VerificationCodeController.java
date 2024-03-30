package mx.edu.utez.server.modules.verification_code.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.verification_code.service.VerificationCodeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/verification-code")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class VerificationCodeController {
    private final VerificationCodeService verificationCodeService;
}