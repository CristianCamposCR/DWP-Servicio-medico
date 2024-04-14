package mx.edu.utez.server.modules.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.auth.controller.dto.SignedDto;
import mx.edu.utez.server.modules.auth.controller.dto.SignupDto;
import mx.edu.utez.server.modules.auth.controller.dto.UserSignInDto;
import mx.edu.utez.server.modules.auth.service.AuthService;
import mx.edu.utez.server.modules.patient.service.PatientService;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final PatientService patientService;
    private final AuthService authService;

    @PostMapping("/signup/")
    public ResponseEntity<ResponseApi<Boolean>> signup(@Valid @RequestBody SignupDto signupDto) {
        try {
            ResponseApi<Boolean> responseApi = this.patientService.saveToSignup(signupDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApi<SignedDto>> login(@Valid @RequestBody UserSignInDto userSigninDto) {
        ResponseApi<SignedDto> responseApi = authService.signIn(userSigninDto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}