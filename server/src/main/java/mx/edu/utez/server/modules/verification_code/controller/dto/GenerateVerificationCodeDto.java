package mx.edu.utez.server.modules.verification_code.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenerateVerificationCodeDto {
    @NotBlank
    private String username;
    @Positive
    private Integer mode;
}
