package mx.edu.utez.server.modules.appointment.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.doctor.model.Doctor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignDto {
    @NotNull
    private Doctor doctor;
    @NotNull
    @Positive
    private Integer scheduledHour;
}
