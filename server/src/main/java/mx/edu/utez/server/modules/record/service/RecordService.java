package mx.edu.utez.server.modules.record.service;

import mx.edu.utez.server.modules.record.model.IRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecordService {
    private final IRecordRepository iRecordRepository;

    public RecordService(IRecordRepository iRecordRepository) {
        this.iRecordRepository = iRecordRepository;
    }
}
