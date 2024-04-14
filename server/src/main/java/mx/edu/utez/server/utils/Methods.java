package mx.edu.utez.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.security.entities.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Methods {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 5;

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

    public static String getFullName(Person person) {
        return (person.getName() + " " + person.getSurname() + " " + (person.getLastname() != null ? person.getLastname() : "")).trim();
    }

    public static String formatScheduledAt(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(format, new Locale("es", "ES"));
        String formatDate = formatter.format(localDate);
        return formatDate.substring(0, 1).toUpperCase() + formatDate.substring(1);
    }

    public static String formatLocalDateTime(Instant localTime, String format, String zonedId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(zonedId));
        return formatter.format(localTime);
    }

    public static String getLoggedUsername() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

    public static String getTableName(String serviceName) {
        String entityName = serviceName.substring(0, serviceName.lastIndexOf("Service"));
        return entityName.replaceAll("(\\p{Ll})(\\p{Lu})", "$1_$2").toLowerCase() + "s";
    }
}