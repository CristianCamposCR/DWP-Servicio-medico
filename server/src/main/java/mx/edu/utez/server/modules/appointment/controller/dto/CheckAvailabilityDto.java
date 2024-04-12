package mx.edu.utez.server.modules.appointment.controller.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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
    @Future(groups = {Scheduling.class})
    @FutureOrPresent(groups = {Assingning.class})
    private LocalDate scheduledAt;
    @NotNull(groups = {Scheduling.class, Assingning.class})
    private Speciality speciality;
    @NotNull(groups = {Scheduling.class, Assingning.class})
    private Shift shift;

    public interface Scheduling{}
    public interface Assingning{}
}
