package mx.edu.utez.server.modules.verification_code.controller.open;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.verification_code.controller.dto.GenerateVerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.controller.dto.VerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.service.VerificationCodeService;
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
@RequestMapping(value = "/api/open/verification-code/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class VerificationCodeController {
    Logger logger = LoggerFactory.getLogger(VerificationCodeController.class);
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/verify")
    public ResponseEntity<ResponseApi<Boolean>> verifyCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto) {
        ResponseApi<Boolean> responseApi = this.verificationCodeService.verifyCode(verificationCodeDto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PostMapping("/activate_account/")
    public ResponseEntity<ResponseApi<Boolean>> activateAccount(@Valid @RequestBody VerificationCodeDto dto) {
        try {
            ResponseApi<Boolean> responseApi = this.verificationCodeService.activateAccount(dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/refresh-activation-code/")
    public ResponseEntity<ResponseApi<Boolean>> refreshCodeForActivation(@Valid @RequestBody GenerateVerificationCodeDto dto) {
        try {
            ResponseApi<Boolean> responseApi = this.verificationCodeService.refreshCode(dto, true);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/refresh-code/")
    public ResponseEntity<ResponseApi<Boolean>> refreshCode(@Valid @RequestBody GenerateVerificationCodeDto dto) {
        try {
            ResponseApi<Boolean> responseApi = this.verificationCodeService.refreshCode(dto, false);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}