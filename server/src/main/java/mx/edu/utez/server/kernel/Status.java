package mx.edu.utez.server.kernel;

public enum Status {
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
