package mx.edu.utez.server.modules.auth.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.server.modules.gender.model.Gender;

import java.time.LocalDate;

@Data
public class SignupDto {
    // Person
    @NotNull(message = "Nombre no debe ser nulo")
    private String name;
    private String surname;
    private String lastname;
    private String phoneNumber;
    private String curp;
    private LocalDate birthday;
    private String email;
    private Gender gender;
    // User
    private String username;
    private String password;
}
