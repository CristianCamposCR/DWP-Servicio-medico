package mx.edu.utez.server.modules.auth.controller.dto;

import lombok.Data;

@Data
public class UserSignInDto {
    private String username;
    private String password;
}
