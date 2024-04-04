package mx.edu.utez.server.modules.verification_code.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationCodeDto {
    @NotBlank
    private String code;
}
