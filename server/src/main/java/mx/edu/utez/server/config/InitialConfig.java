package mx.edu.utez.server.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Genders;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.kernel.Shifts;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.appointment_type.service.AppointmentTypeService;
import mx.edu.utez.server.modules.cancellation_reason.service.CancellationReasonService;
import mx.edu.utez.server.modules.gender.service.GenderService;
import mx.edu.utez.server.modules.role.service.RoleService;
import mx.edu.utez.server.modules.shift.service.ShiftService;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.status.service.StatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {
    private final StatusService statusService;
    private final AppointmentTypeService appointmentTypeService;
    private final CancellationReasonService cancellationReasonService;
    private final GenderService genderService;
    private final ShiftService shiftService;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        // STATUSES
        // - Usuarios
        Status activeStatusU = this.statusService.findOrSave(Statuses.ACTIVO, StatusType.USUARIOS);
        this.statusService.findOrSave(Statuses.INACTIVO, StatusType.USUARIOS);
        this.statusService.findOrSave(Statuses.BLOQUEADO, StatusType.USUARIOS);
        // - Generales
        Status activeStatusG = this.statusService.findOrSave(Statuses.ACTIVO, StatusType.GENERAL);
        this.statusService.findOrSave(Statuses.INACTIVO, StatusType.GENERAL);
        // - Citas
        this.statusService.findOrSave(Statuses.AGENDADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.REAGENDADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.CONFIRMADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.ATENDIDA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.CANCELADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.EXPIRADA, StatusType.CITAS);
        // - Pagos
        this.statusService.findOrSave(Statuses.PAGADO, StatusType.PAGOS);
        this.statusService.findOrSave(Statuses.REEMBOLSADO, StatusType.PAGOS);

        // APPOINTMENT TYPES
        this.appointmentTypeService.findOrSave("Cita de control", "Se realiza para supervisar la evolución de una enfermedad ya diagnosticada con un especialista en la materia.", activeStatusG);
        this.appointmentTypeService.findOrSave("Cita preoperatoria", "Se efectúa antes de una cirugía para informar al paciente sobre el procedimiento, riesgos y postoperatorio.", activeStatusG);
        this.appointmentTypeService.findOrSave("Cita de seguimiento", "Se lleva a cabo como parte de la rehabilitación tras una intervención quirúrgica para evaluar la evolución del paciente.", activeStatusG);
        this.appointmentTypeService.findOrSave("Cita por dolencia", "Se agenda cuando el paciente presenta síntomas que requieren atención médica especializada.", activeStatusG);
        this.appointmentTypeService.findOrSave("Cita de urgencia", "Se proporciona atención inmediata para afecciones que requieren intervención médica urgente, sin necesidad de cita previa.", activeStatusG);
        // CANCELLATION REASON
        this.cancellationReasonService.findOrSave("Sin disponibilidad", 100, activeStatusG);
        this.cancellationReasonService.findOrSave("No atendida", 100, activeStatusG);
        this.cancellationReasonService.findOrSave("Ausencia", 0, activeStatusG);
        this.cancellationReasonService.findOrSave("Paciente", 50, activeStatusG);
        // GENDER
        this.genderService.findOrSave(Genders.FEMENINO, activeStatusG);
        this.genderService.findOrSave(Genders.MASCULINO, activeStatusG);
        this.genderService.findOrSave(Genders.OTRO, activeStatusG);
        // SHIFTS
        this.shiftService.findOrSave(Shifts.MATUTINO, 6, 12, activeStatusG);
        this.shiftService.findOrSave(Shifts.VESPERTINO, 12, 18, activeStatusG);
        // ROLE
        this.roleService.findOrSave(Roles.ADMIN, activeStatusG);
        this.roleService.findOrSave(Roles.PATIENT, activeStatusG);
        this.roleService.findOrSave(Roles.DOCTOR, activeStatusG);
        // DEFAULT USER
    }
}
