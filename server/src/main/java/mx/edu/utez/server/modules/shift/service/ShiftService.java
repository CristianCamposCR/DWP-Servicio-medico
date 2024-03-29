package mx.edu.utez.server.modules.shift.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Shifts;
import mx.edu.utez.server.modules.shift.model.IShiftRepository;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftService {
    private final IShiftRepository iShiftRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Shift findOrSave(Shifts name, Integer entryHour, Integer departureHour, Status status) {
        Optional<Shift> optionalShift = this.iShiftRepository.findByName(name);
        return optionalShift.orElseGet(() -> this.iShiftRepository.saveAndFlush(new Shift(name, entryHour, departureHour, status)));
    }
}