package mx.edu.utez.server.modules.appointment.controller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckAvailabilityDto {
    @Future
    private LocalDate scheduledAt;
    @NotNull
    private Speciality speciality;
    @NotNull
    private Shift shift;
}
