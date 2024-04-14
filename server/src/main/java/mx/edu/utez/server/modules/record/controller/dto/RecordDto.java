package mx.edu.utez.server.modules.record.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.record.model.Record;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordDto {
    @NotNull(groups = {RecordGroups.Update.class})
    @Positive(groups = {RecordGroups.Update.class})
    private Long id;

    @NotBlank(groups = {RecordGroups.Save.class, RecordGroups.Update.class})
    private String diagnosis;

    private String treatmentNotes;

    @NotBlank(groups = {RecordGroups.Save.class, RecordGroups.Update.class})
    private String medications;

    @NotNull(groups = {RecordGroups.Save.class, RecordGroups.Update.class})
    private Appointment appointment;

    public Record getRecordEntity() {
        return new Record(
                getId(),
                getDiagnosis(),
                getTreatmentNotes(),
                getMedications(),
                getAppointment()
        );
    }
}
