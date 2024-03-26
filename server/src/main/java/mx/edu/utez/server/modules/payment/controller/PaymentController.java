package mx.edu.utez.server.modules.payment.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.payment.service.PaymentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/payment")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
}