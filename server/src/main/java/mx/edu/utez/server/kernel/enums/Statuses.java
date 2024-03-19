package mx.edu.utez.server.kernel.enums;

public enum Statuses {
    // Usuarios y general
    ACTIVO,
    INACTIVO,

    // Usuario
    BLOQUEADO,

    // Citas
    CONFIRMADA,
    ATENDIDA,
    AGENDADA,
    CANCELADA,
    EXPIRADA,

    // Doctores
    OCUPADO,
    LIBRE,
}
