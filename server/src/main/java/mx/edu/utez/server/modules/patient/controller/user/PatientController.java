package mx.edu.utez.server.modules.patient.controller.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.patient.service.PatientService;
import mx.edu.utez.server.modules.security.entities.UserDetailsImpl;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/patient/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PATIENT')")
public class PatientController {
    Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;

    @GetMapping("/profile/")
    public ResponseEntity<ResponseApi<Patient>> findOne() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();
            logger.info(email);
            ResponseApi<Patient> responseApi = this.patientService.loadProfileByEmail(email);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}