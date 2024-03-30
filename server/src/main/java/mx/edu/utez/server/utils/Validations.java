package mx.edu.utez.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.edu.utez.server.kernel.Errors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
    public static boolean isInvalidId(Long id) {
        return id == null || id <= 0;
    }

    public static void validateInput(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException(Errors.INVALID_FIELDS.name());
        }
    }
}