package mx.edu.utez.server.modules.appointment.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment_type.model.AppointmentType;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.payment.controller.dto.PaymentDto;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveAppointmentDto {
    @Future
    private LocalDate scheduledAt;
    @NotNull
    private Speciality speciality;
    @NotNull
    private AppointmentType appointmentType;
    @NotNull
    private Shift shift;

    @NotNull
    @Valid
    PaymentDto payment;

    public Appointment getAppointmentEntity() {
        return new Appointment(
                getScheduledAt(),
                getSpeciality(),
                getAppointmentType(),
                getShift()
        );
    }
}
