package mx.edu.utez.server.modules.person.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.gender.controller.dto.GenderDto;
import mx.edu.utez.server.modules.person.model.Person;

import java.time.LocalDate;

@Data
public class PersonDto {
    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String surname;

    @Size(max = 50)
    private String lastname;

    @NotBlank
    @Size(min = 2, max = 100)
    private String email;

    @Size(min = 18, max = 18)
    private String curp;

    @Size(min = 10, max = 15)
    private String phoneNumber;

    private String details;

    @Past
    private LocalDate birthday;

    private String profilePhoto;

    // Relationships <-
    @NotNull
    @Valid
    private GenderDto gender;

    public Person getPersonEntity() {
        return new Person(
                getId(),
                getName(),
                getSurname(),
                getLastname(),
                getEmail(),
                getCurp(),
                getPhoneNumber(),
                getDetails(),
                getBirthday(),
                getProfilePhoto(),
                getGender().getGenderEntity()
        );
    }
}
