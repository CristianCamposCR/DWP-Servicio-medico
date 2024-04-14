package mx.edu.utez.server.modules.doctor.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;

@Data
public class UpdateDoctorDto {
    @NotNull
    @Positive
    private Long id;

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

    private String profilePhoto;

    public Doctor getDoctorEntity(){
        return new Doctor(
                getId(),
                getProfessionalId(),
                getExperience(),
                getIsAux(),
                getAvailableDays(),
                getShift(),
                getSpeciality(),
                null
        );
    }
}
