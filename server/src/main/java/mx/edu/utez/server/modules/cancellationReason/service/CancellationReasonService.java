package mx.edu.utez.server.modules.cancellationReason.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.cancellationReason.module.CancellationReason;
import mx.edu.utez.server.modules.cancellationReason.module.ICancellationReasonRepository;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CancellationReasonService {
    private final ICancellationReasonRepository iCancellationReasonRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public CancellationReason findOrSave(String reason, Integer refundPercent, Status status) {
        Optional<CancellationReason> optionalCancellationReason = this.iCancellationReasonRepository.findByReason(reason);
        return optionalCancellationReason.orElseGet(() -> this.iCancellationReasonRepository.saveAndFlush(new CancellationReason(reason, refundPercent, status)));
    }
}