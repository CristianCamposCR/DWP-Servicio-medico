package mx.edu.utez.server.modules.email.controller;

import mx.edu.utez.server.modules.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/enviar-correo")
    public String enviarCorreo(@RequestParam String destinatario, @RequestParam String asunto, @RequestParam String cuerpo) {
        emailService.enviarCorreo(destinatario, asunto, cuerpo);
        return "Correo enviado exitosamente";
    }
}
