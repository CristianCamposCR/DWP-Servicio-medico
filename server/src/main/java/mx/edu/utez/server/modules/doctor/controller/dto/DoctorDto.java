package mx.edu.utez.server.modules.doctor.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.gender.controller.dto.GenderDto;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;

import java.time.LocalDate;

@Data
public class DoctorDto {
    // Doctor
    @NotBlank
    private String professionalId;

    @Positive
    private Long experience;

    @NotNull
    private Boolean isAux;

    @NotBlank
    @Size(min = 1, max = 80)
    private String availableDays;

    @NotNull
    private Shift shift;

    @NotNull
    private Speciality speciality;

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
