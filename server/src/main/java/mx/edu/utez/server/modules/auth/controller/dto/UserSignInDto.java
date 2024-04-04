package mx.edu.utez.server.modules.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignInDto {
    @NotBlank
    @Size(min = 1, max = 100)
    private String username;
    @NotBlank
    private String password;
}
