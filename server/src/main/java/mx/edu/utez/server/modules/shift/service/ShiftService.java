package mx.edu.utez.server.modules.shift.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Shifts;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.shift.model.IShiftRepository;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public ResponseApi<Set<Shift>> openListAll() {
        try {
            Set<Shift> shifts = this.iShiftRepository.findAllByStatus_Name(Statuses.ACTIVO);
            return new ResponseApi<>(
                    shifts,
                    HttpStatus.OK,
                    false,
                    "Lista de tipos de cita."
            );
        } catch (Exception e) {
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }
}