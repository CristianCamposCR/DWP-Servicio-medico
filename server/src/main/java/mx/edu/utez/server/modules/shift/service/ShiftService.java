package mx.edu.utez.server.modules.shift.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.shift.model.IShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftService {
    private IShiftRepository iShiftRepository;
}