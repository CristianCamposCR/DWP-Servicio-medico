package mx.edu.utez.server.modules.gender.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Genders;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.gender.model.IGenderRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class GenderService {
    Logger logger = LoggerFactory.getLogger(GenderService.class);
    private final IGenderRepository iGenderRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Gender findOrSave(Genders name, Status status) {
        Optional<Gender> optionalGender = this.iGenderRepository.findByName(name);
        return optionalGender.orElseGet(() -> this.iGenderRepository.saveAndFlush(new Gender(name, status)));
    }

    @Transactional(readOnly = true)
    public ResponseApi<Set<Gender>> openListAll() {
        try {
            Set<Gender> genders = this.iGenderRepository.findAllByStatus_Name(Statuses.ACTIVO);
            return new ResponseApi<>(
                    genders,
                    HttpStatus.OK,
                    false,
                    "Lista de tipos de cita."
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }
}