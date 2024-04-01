package mx.edu.utez.server.modules.patient.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.auth.controller.dto.SignupDto;
import mx.edu.utez.server.modules.patient.model.IPatientRepository;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.person.model.IPersonRepository;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.role.model.IRoleRepository;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final IPatientRepository iPatientRepository;
    private final IUserRepository iUserRepository;
    private final IStatusRepository iStatusRepository;
    private final IRoleRepository iRoleRepository;
    private final IPersonRepository iPersonRepository;
    private final HashService hashService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Boolean> signup(SignupDto signupDto) {
        try {
            String fullName = (signupDto.getName() + " " + signupDto.getSurname() + " " + (signupDto.getLastname() != null ? signupDto.getLastname() : "")).trim();
            String originalPassword = hashService.decrypt(signupDto.getPassword());
            if (this.iPatientRepository.existsByFullNameOrCurpOrEmail(fullName, signupDto.getCurp(), signupDto.getEmail()) > 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_PATIENT.name());

            if (this.iUserRepository.existsByUsernameIgnoreCase(signupDto.getUsername()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_USER.name());

            if (originalPassword == null)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.NO_VERIFICADO, StatusType.USUARIOS);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            Optional<Role> optionalRole = this.iRoleRepository.findByName(Roles.PATIENT);
            if (optionalRole.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_ROLE_FOUND.name());

            String patientCode = generatePatientCode(
                    signupDto.getName(),
                    signupDto.getSurname(),
                    signupDto.getBirthday()
            );

            Person person = this.iPersonRepository.saveAndFlush(new Person(
                    signupDto.getName(),
                    signupDto.getSurname(),
                    signupDto.getLastname(),
                    signupDto.getEmail(),
                    signupDto.getCurp(),
                    signupDto.getPhoneNumber(),
                    signupDto.getBirthday(),
                    signupDto.getGender().getGenderEntity()
            ));

            this.iUserRepository.saveAndFlush(new User(
                    signupDto.getUsername(),
                    false,
                    passwordEncoder.encode(originalPassword),
                    optionalStatus.get(),
                    optionalRole.get(),
                    person
            ));

            this.iPatientRepository.saveAndFlush(new Patient(
                    patientCode,
                    optionalStatus.get(),
                    person
            ));

            return new ResponseApi<>(
                    true,
                    HttpStatus.CREATED,
                    false,
                    "Paciente creado"
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

    public String generatePatientCode(String name, String surname, LocalDate yearBirth) {
        String namePart = name.substring(0, Math.min(name.length(), 2)).toUpperCase();
        String surnamePart = surname.substring(0, Math.min(surname.length(), 2)).toUpperCase();
        String year = Integer.toString(yearBirth.getYear());
        Long patientsCount = this.iPatientRepository.count();
        String consecutiveId = String.format("%04d", patientsCount);

        // Concatenar todos los componentes para formar el código
        return namePart + surnamePart + year + consecutiveId;
    }
}