package mx.edu.utez.server.modules.payment.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.payment.module.IPaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final IPaymentRepository iPaymentRepository;
}