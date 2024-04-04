package mx.edu.utez.server.modules.verification_code.controller.open;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.verification_code.controller.dto.VerificationCodeDto;
import mx.edu.utez.server.modules.verification_code.service.VerificationCodeService;
import mx.edu.utez.server.utils.ResponseApi;
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
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/verify")
    public ResponseEntity<ResponseApi<Boolean>> verifyCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto) {
        ResponseApi<Boolean> responseApi = this.verificationCodeService.verifyCode(verificationCodeDto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}