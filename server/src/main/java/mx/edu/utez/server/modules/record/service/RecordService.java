package mx.edu.utez.server.modules.record.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.record.model.IRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final IRecordRepository iRecordRepository;
}
