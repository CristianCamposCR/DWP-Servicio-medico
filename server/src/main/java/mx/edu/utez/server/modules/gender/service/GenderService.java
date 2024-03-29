package mx.edu.utez.server.modules.gender.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Genders;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.gender.model.IGenderRepository;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GenderService {
    private final IGenderRepository iGenderRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Gender findOrSave(Genders name, Status status) {
        Optional<Gender> optionalGender = this.iGenderRepository.findByName(name);
        return optionalGender.orElseGet(() -> this.iGenderRepository.saveAndFlush(new Gender(name, status)));
    }
}