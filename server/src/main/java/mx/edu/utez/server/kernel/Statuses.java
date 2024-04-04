package mx.edu.utez.server.kernel;

public enum Statuses {
    // Usuarios y general
    ACTIVO,
    INACTIVO,

    // Usuario
    BLOQUEADO,
    NO_VERIFICADO,

    // Citas
    AGENDADA,
    REAGENDADA,
    CONFIRMADA,
    ATENDIDA,
    CANCELADA,
    EXPIRADA,

    // Doctores
    OCUPADO,
    LIBRE,

    // Pagos
    PAGADO,
    REEMBOLSADO,
}
