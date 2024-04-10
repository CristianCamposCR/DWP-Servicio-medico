package mx.edu.utez.server.modules.person.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.gender.model.IGenderRepository;
import mx.edu.utez.server.modules.person.controller.dto.PersonDto;
import mx.edu.utez.server.modules.person.model.IPersonRepository;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final IPersonRepository iPersonRepository;
    private final IGenderRepository iGenderRepository;

    @Transactional(readOnly = true)
    public ResponseApi<Person> findOne(Long id, String username) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Person> optionalPerson = this.iPersonRepository.findByIdAndUser_Status_NameNot(id, Statuses.INACTIVO);
            if (optionalPerson.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PERSON_FOUND.name());

            Optional<Person> existentPerson = this.iPersonRepository.findByUser_UsernameIgnoreCase(username);
            if (!Objects.equals(existentPerson.get().getId(), id))
                return new ResponseApi<>(HttpStatus.FORBIDDEN, true, Errors.CANNOT_ACCESS_TO_RESOURCE.name());

            return new ResponseApi<>(optionalPerson.get(), HttpStatus.OK, false, "Person.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    Errors.SERVER_ERROR.name()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Person> update(PersonDto personDto, String username) {
        try {
            if (!this.iPersonRepository.existsByIdAndUser_Status_NameNot(personDto.getId(), Statuses.INACTIVO))
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PERSON_FOUND.name());

            Optional<Person> existentPerson = this.iPersonRepository.findByUser_UsernameIgnoreCase(username);
            if (!Objects.equals(existentPerson.get().getId(), personDto.getId()))
                return new ResponseApi<>(HttpStatus.FORBIDDEN, true, Errors.CANNOT_ACCESS_TO_RESOURCE.name());

            String fullName = (personDto.getName() + " " + personDto.getSurname() + " " + (personDto.getLastname() != null ? personDto.getLastname() : "")).trim();

            Optional<Person> optionalPerson = this.iPersonRepository.findByFullnameOrCurpOrEmailAndIdNot(fullName, personDto.getCurp(), personDto.getEmail(), personDto.getId());
            if (optionalPerson.isPresent())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_PERSON.name());

            if (!this.iGenderRepository.existsById(personDto.getGender().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_GENDER_FOUND.name());

            return new ResponseApi<>(
                    this.iPersonRepository.saveAndFlush(personDto.getPersonEntity()),
                    HttpStatus.OK,
                    false,
                    "Persona actualizada."
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
