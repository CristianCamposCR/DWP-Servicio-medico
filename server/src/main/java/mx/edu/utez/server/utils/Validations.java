package mx.edu.utez.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.edu.utez.server.kernel.Errors;

import java.time.Instant;

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

    public static boolean isExpiredCode(Instant expireAt) {
        Instant currentInstant = Instant.now();
        return currentInstant.isAfter(expireAt);
    }
}