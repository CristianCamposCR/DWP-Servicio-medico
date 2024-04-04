package mx.edu.utez.server.modules.doctor.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.person.model.IPersonRepository;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {
    private final IDoctorRepository iDoctorRepository;
    private final IPersonRepository iPersonRepository;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;

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
}