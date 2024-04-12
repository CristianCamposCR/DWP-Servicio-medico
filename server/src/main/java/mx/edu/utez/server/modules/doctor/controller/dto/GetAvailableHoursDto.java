package mx.edu.utez.server.modules.doctor.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAvailableHoursDto {
    @NotNull
    @Positive
    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDate scheduledAt;
}
