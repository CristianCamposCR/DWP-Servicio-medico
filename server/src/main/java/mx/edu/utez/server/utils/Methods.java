package mx.edu.utez.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.security.entities.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Methods {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 10;

    public static String convertToString(Object data) {
        if (data == null)
            throw new IllegalArgumentException(Errors.INVALID_FIELDS.name());

        return String.valueOf(data);
    }

    public static String generateRandomString() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String getLoggedUsername() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
}