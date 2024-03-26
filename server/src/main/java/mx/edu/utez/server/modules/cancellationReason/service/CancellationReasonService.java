package mx.edu.utez.server.modules.cancellationReason.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.cancellationReason.module.ICancellationReasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CancellationReasonService {
    private final ICancellationReasonRepository iCancellationReasonRepository;
}