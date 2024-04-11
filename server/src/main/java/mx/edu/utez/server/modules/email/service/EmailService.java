package mx.edu.utez.server.modules.email.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.email.controller.dto.EmailDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public boolean sendMail(EmailDto email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getEmail());
            helper.setSubject(email.getSubject());
            helper.setText(template(email), true);
            mailSender.send((message));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String template(EmailDto email) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Plantilla de Correo Electrónico</title>\n" +
                "    <style>\n" +
                "        /* Estilos generales */\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
                "        }\n" +
                "        .card {\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .title-card {\n" +
                "            background-color: #10b981;\n" +
                "            color: #fff;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content-card {\n" +
                "            background-color: #f9f9f9;\n" +
                "            box-shadow: 0 0 5px rgba(0,0,0,0.1);\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #666;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            color: #fff;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "       .separator {\n" +
                "            border-top: 1px solid #ddd;\n" +
                "            margin-top: 20px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .ticket {\n" +
                "            background-color: #f3f3f3;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .ticket p {\n" +
                "            margin-bottom: 5px;\n" +
                "        }" +
                "\n" +
                "        /* Estilos específicos para dispositivos móviles */\n" +
                "        @media only screen and (max-width: 600px) {\n" +
                "            .container {\n" +
                "                margin: 10px auto;\n" +
                "                padding: 10px;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <!-- Card del título con color de fondo \"#10b981\" y centrado -->\n" +
                "        <div class=\"card title-card\">\n" +
                "            <h1>" + email.getTitle() + "</h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Card de contenido con color de fondo gris claro -->\n" +
                "        <div class=\"card content-card\">\n" +
                (email.getFullName() != null ? "<p>Hola, " + email.getFullName() + ".</p>\n" : "") +
                "            <p>" + email.getBody() + "</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Separator -->\n" +
                "        <div class=\"separator\"></div>\n" +
                "        <!-- Footer -->\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Este es un correo electrónico generado automáticamente. Por favor, no responda a este mensaje.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
