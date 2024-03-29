package mx.edu.utez.server.utils;

public class Validations {
    public static boolean isInvalidId(Long id) {
        return id == null || id <= 0;
    }
}
