package mx.edu.utez.server.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.status.service.StatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {
    private final StatusService statusService;

    @Override
    public void run(String... args) throws Exception {
        // Usuarios
        Status activeStatusU = this.statusService.findOrSave(Statuses.ACTIVO, StatusType.USUARIOS);
        this.statusService.findOrSave(Statuses.INACTIVO, StatusType.USUARIOS);
        this.statusService.findOrSave(Statuses.BLOQUEADO, StatusType.USUARIOS);
        // Generales
        Status activeStatusG = this.statusService.findOrSave(Statuses.ACTIVO, StatusType.GENERAL);
        this.statusService.findOrSave(Statuses.INACTIVO, StatusType.GENERAL);
        // Citas
        this.statusService.findOrSave(Statuses.AGENDADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.REAGENDADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.CONFIRMADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.ATENDIDA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.CANCELADA, StatusType.CITAS);
        this.statusService.findOrSave(Statuses.EXPIRADA, StatusType.CITAS);
        // Doctores
        this.statusService.findOrSave(Statuses.OCUPADO, StatusType.DOCTORES);
        this.statusService.findOrSave(Statuses.LIBRE, StatusType.DOCTORES);
        // Pagos
        this.statusService.findOrSave(Statuses.PAGADO, StatusType.PAGOS);
        this.statusService.findOrSave(Statuses.REEMBOLSADO, StatusType.PAGOS);
    }
}
