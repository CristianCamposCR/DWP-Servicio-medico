package mx.edu.utez.server.modules.status.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatusService {
    private final IStatusRepository iStatusRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Status findOrSave(Statuses name, StatusType statusType) {
        Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(name, statusType);
        return optionalStatus.orElseGet(() -> this.iStatusRepository.saveAndFlush(new Status(name, statusType)));
    }
}
