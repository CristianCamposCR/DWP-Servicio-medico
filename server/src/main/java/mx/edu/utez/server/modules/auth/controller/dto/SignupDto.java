package mx.edu.utez.server.modules.auth.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.gender.controller.dto.GenderDto;

import java.time.LocalDate;

@Data
public class SignupDto {
    // Person
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @NotBlank
    @Size(min = 1, max = 45)
    private String surname;
    @Size(max = 50)
    private String lastname;
    @NotBlank
    @Size(min = 1, max = 100)
    private String email;
    @NotBlank
    @Size(min = 1, max = 15)
    private String phoneNumber;
    @NotBlank
    @Size(min = 1, max = 18)
    private String curp;
    @NotNull
    @Past
    private LocalDate birthday;
    @Valid
    private GenderDto gender;
    // User
    @NotBlank
    @Size(min = 1, max = 100)
    private String username;
    @NotBlank
    private String password;
}
