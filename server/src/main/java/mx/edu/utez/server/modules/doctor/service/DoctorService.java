package mx.edu.utez.server.modules.doctor.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.*;
import mx.edu.utez.server.modules.appointment.model.IAppointmentRepository;
import mx.edu.utez.server.modules.doctor.controller.dto.DoctorDto;
import mx.edu.utez.server.modules.doctor.controller.dto.UpdateDoctorDto;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.gender.model.IGenderRepository;
import mx.edu.utez.server.modules.person.model.IPersonRepository;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.role.model.IRoleRepository;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.shift.model.IShiftRepository;
import mx.edu.utez.server.modules.speciality.model.ISpecialityRepository;
import mx.edu.utez.server.modules.status.model.IStatusRepository;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {
    Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final IDoctorRepository iDoctorRepository;
    private final IPersonRepository iPersonRepository;
    private final IUserRepository iUserRepository;
    private final IShiftRepository iShiftRepository;
    private final ISpecialityRepository iSpecialityRepository;
    private final IGenderRepository iGenderRepository;
    private final IStatusRepository iStatusRepository;
    private final IRoleRepository iRoleRepository;
    private final IAppointmentRepository iAppointmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final HashService hashService;

    @Value("${default.user.username}")
    private String defaultUsername;

    @Value("${default.user.password}")
    private String defaultPassword;

    public void findOrSaveAdmin(Gender genderM, Status activeStatusU, Role adminRole) {
        Person person = new Person(
                "David Ivan", "Pérez", "Torres",
                "davirperez@utez.edu.mx", "GAPJ950825HMCRRN00", "+7778889900",
                LocalDate.of(1990, 5, 15), genderM);

        User user = new User(
                defaultUsername,
                false,
                passwordEncoder.encode(defaultPassword),
                activeStatusU,
                adminRole,
                null
        );

        String fullName = (person.getName() + " " + person.getSurname() + " " + (person.getLastname() != null ? person.getLastname() : "")).trim();
        if (this.iDoctorRepository.existsByFullNameOrCurpOrEmail(fullName, person.getCurp(), person.getEmail()) > 0 ||
                this.iUserRepository.existsByUsernameIgnoreCase(user.getUsername()))
            return;

        Person savedPerson = this.iPersonRepository.saveAndFlush(person);
        user.setPerson(savedPerson);

        this.iUserRepository.saveAndFlush(user);
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Doctor> save(DoctorDto doctorDto) {
        try {
            String fullName = (doctorDto.getName() + " " + doctorDto.getSurname() + " " + (doctorDto.getLastname() != null ? doctorDto.getLastname() : "")).trim();
            String originalPassword = hashService.decrypt(doctorDto.getPassword());

            if (this.iDoctorRepository.existsByFullNameOrCurpOrEmailOrProfessionalId(
                    fullName, doctorDto.getCurp(), doctorDto.getEmail(), doctorDto.getProfessionalId()) > 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_DOCTOR.name());

            if (this.iUserRepository.existsByUsernameIgnoreCase(doctorDto.getUsername()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_DOCTOR.name());

            if (!this.iShiftRepository.existsById(doctorDto.getShift().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_SHIFT_FOUND.name());

            if (!specialityExists(doctorDto.getSpeciality().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_SPECIALITY_FOUND.name());

            if (!specialityIsActive(doctorDto.getSpeciality().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SPECIALITY_IS_INACTIVE.name());

            if (!this.iGenderRepository.existsById(doctorDto.getGender().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_GENDER_FOUND.name());

            if (originalPassword == null)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Status> optionalStatus = this.iStatusRepository.findByNameAndStatusType(Statuses.ACTIVO, StatusType.USUARIOS);
            if (optionalStatus.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_STATUS_FOUND.name());

            Optional<Role> optionalRole = this.iRoleRepository.findByName(Roles.DOCTOR);
            if (optionalRole.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_ROLE_FOUND.name());

            Person person = this.iPersonRepository.saveAndFlush(new Person(
                    doctorDto.getName(),
                    doctorDto.getSurname(),
                    doctorDto.getLastname(),
                    doctorDto.getEmail(),
                    doctorDto.getCurp(),
                    doctorDto.getPhoneNumber(),
                    doctorDto.getBirthday(),
                    doctorDto.getGender().getGenderEntity()
            ));

            this.iUserRepository.saveAndFlush(new User(
                    doctorDto.getUsername(),
                    false,
                    passwordEncoder.encode(originalPassword),
                    optionalStatus.get(),
                    optionalRole.get(),
                    person
            ));

            Doctor doctor = new Doctor(
                    doctorDto.getProfessionalId(),
                    doctorDto.getExperience(),
                    doctorDto.getIsAux(),
                    doctorDto.getAvailableDays(),
                    doctorDto.getShift(),
                    doctorDto.getSpeciality(),
                    person
            );

            return new ResponseApi<>(
                    this.iDoctorRepository.saveAndFlush(doctor),
                    HttpStatus.CREATED,
                    false,
                    "Doctor creado."
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
    public ResponseApi<Page<Doctor>> findAll(SearchDto searchDto, Pageable pageable) {
        try {
            Page<Doctor> doctors;
            if (searchDto != null && searchDto.getSearchValue() != null && !searchDto.getSearchValue().isBlank()) {
                doctors = this.iDoctorRepository.findAllBySearchValue(searchDto.getSearchValue(), pageable);
            } else {
                doctors = this.iDoctorRepository.findAll(pageable);
            }

            return new ResponseApi<>(
                    doctors,
                    HttpStatus.OK,
                    false,
                    "Doctores."
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
    public ResponseApi<Page<Doctor>> openFindAll(SearchDto searchDto, Pageable pageable) {
        try {
            Page<Doctor> doctors;
            if (searchDto != null && searchDto.getSearchValue() != null && !searchDto.getSearchValue().isBlank()) {
                doctors = this.iDoctorRepository.findAllBySearchValueAndStatusNameNot(searchDto.getSearchValue(), Statuses.INACTIVO, pageable);
            } else {
                doctors = this.iDoctorRepository.findAllByPerson_User_Status_NameNot(Statuses.INACTIVO, pageable);
            }

            return new ResponseApi<>(
                    doctors,
                    HttpStatus.OK,
                    false,
                    "Doctores."
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
    public ResponseApi<Doctor> findOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Doctor> optionalDoctor = this.iDoctorRepository.findById(id);
            return optionalDoctor.map(doctor ->
                    new ResponseApi<>(doctor, HttpStatus.OK, false, "Doctor.")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_DOCTOR_FOUND.name())
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
    public ResponseApi<Doctor> openFindOne(Long id) {
        try {
            if (Validations.isInvalidId(id))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Doctor> optionalDoctor = this.iDoctorRepository.findByIdAndPerson_User_StatusNameNot(id, Statuses.INACTIVO);
            return optionalDoctor.map(doctor ->
                    new ResponseApi<>(doctor, HttpStatus.OK, false, "Doctor.")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_DOCTOR_FOUND.name())
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

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<Doctor> update(UpdateDoctorDto dto) {
        try {
            Optional<Doctor> optionalDoctor = this.iDoctorRepository.findById(dto.getId());
            if (optionalDoctor.isEmpty())
                return new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_DOCTOR_FOUND.name());

            if(this.iAppointmentRepository.existByDoctorId(dto.getId(), Instant.now()) > 0)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DOCTOR_HAS_DEPENDENCIES.name());

            if (this.iDoctorRepository.existsByProfessionalIdAndIdNot(dto.getProfessionalId(), dto.getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.DUPLICATED_DOCTOR.name());

            if (!this.iShiftRepository.existsById(dto.getShift().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_SHIFT_FOUND.name());

            if (!specialityExists(dto.getSpeciality().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_SPECIALITY_FOUND.name());

            if (!specialityIsActive(dto.getSpeciality().getId()))
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.SPECIALITY_IS_INACTIVE.name());

            Doctor doctor = dto.getDoctorEntity();
            doctor.setPerson(optionalDoctor.get().getPerson());

            return new ResponseApi<>(
                    this.iDoctorRepository.saveAndFlush(doctor),
                    HttpStatus.OK,
                    false,
                    "Doctor actualizado."
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
    public ResponseApi<Doctor> loadProfileByEmail(String email) {
        try {
            if (email == null || email.isBlank())
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<Doctor> optionalDoctor = this.iDoctorRepository.findByPerson_User_UsernameAndPerson_User_Status_Name_Not(email, Statuses.INACTIVO);
            return optionalDoctor.map(doctor ->
                    new ResponseApi<>(doctor, HttpStatus.OK, false, "Doctor profile.")
            ).orElseGet(() ->
                    new ResponseApi<>(HttpStatus.NOT_FOUND, true, Errors.NO_DOCTOR_FOUND.name())
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

    private boolean specialityExists(Long specialityId) {
        return this.iSpecialityRepository.existsById(specialityId);
    }

    private boolean specialityIsActive(Long specialityId) {
        return this.iSpecialityRepository.existsByIdAndStatus_Name(specialityId, Statuses.ACTIVO);
    }
}