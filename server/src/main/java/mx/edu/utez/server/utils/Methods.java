package mx.edu.utez.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.edu.utez.server.kernel.Errors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Methods {
    public static String convertToString(Object data) {
        if (data == null)
            throw new IllegalArgumentException(Errors.INVALID_FIELDS.name());

        return String.valueOf(data);
    }
}