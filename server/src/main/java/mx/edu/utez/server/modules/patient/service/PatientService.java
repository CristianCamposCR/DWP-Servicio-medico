package mx.edu.utez.server.modules.patient.service;

import com.twilio.exception.ApiException;
import jakarta.mail.MessagingException;
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
import mx.edu.utez.server.modules.sms.controller.dto.SmsDto;
import mx.edu.utez.server.modules.sms.service.SmsService;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.modules.verification_code.service.VerificationCodeService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.SearchDto;
import mx.edu.utez.server.utils.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final SmsService smsService;
    private final VerificationCodeService verificationCodeService;

    @Transactional(rollbackFor = {SQLException.class, Exception.class, MessagingException.class})
    public ResponseApi<Boolean> signup(SignupDto signupDto) {
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

        User user = this.iUserRepository.saveAndFlush(new User(
                signupDto.getUsername(),
                false,
                passwordEncoder.encode(originalPassword),
                optionalStatus.get(),
                optionalRole.get(),
                person
        ));

        this.iPatientRepository.saveAndFlush(new Patient(
                patientCode,
                person
        ));

        SmsDto smsDto = new SmsDto(
                user.getPerson().getPhoneNumber(),
                "Este es tú código de verificación: " + this.verificationCodeService.generateVerificationCode(user));

        if (!this.smsService.sendSms(smsDto))
            throw new ApiException(Errors.ERROR_SENDING_CODE.name());

        return new ResponseApi<>(
                true,
                HttpStatus.CREATED,
                false,
                "Paciente creado"
        );
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<Patient>> findAll(SearchDto searchDto, Pageable pageable) {
        try {
            Page<Patient> patients;
            if (searchDto != null && searchDto.getSearchValue() != null && !searchDto.getSearchValue().isBlank()) {
                patients = this.iPatientRepository.findAllBySearchValueAndStatusNameNot(searchDto.getSearchValue(), Statuses.NO_VERIFICADO, pageable);
            } else {
                patients = this.iPatientRepository.findAllByPerson_User_Status_NameNot(Statuses.NO_VERIFICADO, pageable);
            }

            return new ResponseApi<>(
                    patients,
                    HttpStatus.OK,
                    false,
                    "Pacientes."
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

    @Transactional(readOnly = true)
    public ResponseApi<Patient> findOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Patient> optionalPatient = this.iPatientRepository.findByIdAndPerson_User_StatusNameNot(id, Statuses.NO_VERIFICADO);
            return optionalPatient.map(patient ->
                    new ResponseApi<>(patient, HttpStatus.OK, false, "Patient")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PATIENT_FOUND.name())
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

    @Transactional(readOnly = true)
    public ResponseApi<Patient> loadProfileByEmail(String email) {
        try {
            if (email == null || email.isBlank())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Patient> optionalPatient = this.iPatientRepository.findByPerson_User_UsernameAndPerson_User_Status_Name_Not(email, Statuses.NO_VERIFICADO);
            return optionalPatient.map(patient ->
                    new ResponseApi<>(patient, HttpStatus.OK, false, "Patient profile.")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_PATIENT_FOUND.name())
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