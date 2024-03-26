package mx.edu.utez.server.kernel;

public enum Statuses {
    // Usuarios y general
    ACTIVO,
    INACTIVO,

    // Usuario
    BLOQUEADO,

    // Citas
    AGENDADA,
    CONFIRMADA,
    ATENDIDA,
    CANCELADA,
    EXPIRADA,

    // Doctores
    OCUPADO,
    LIBRE,
}
