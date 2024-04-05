package mx.edu.utez.server.modules.email.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, true); // true indica que el cuerpo es HTML
            emailSender.send(message);
        } catch (MessagingException e) {
            // Manejar errores
            e.printStackTrace();
        }
    }

}
