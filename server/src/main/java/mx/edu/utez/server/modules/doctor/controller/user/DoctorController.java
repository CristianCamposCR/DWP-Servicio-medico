package mx.edu.utez.server.modules.doctor.controller.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.doctor.service.DoctorService;
import mx.edu.utez.server.modules.security.entities.UserDetailsImpl;
import mx.edu.utez.server.utils.HashService;
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
@RequestMapping(value = "/api/doctor/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('DOCTOR')")
public class DoctorController {
    Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;

    @GetMapping("/profile/")
    public ResponseEntity<ResponseApi<Doctor>> findOne() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();
            logger.info(email);
            ResponseApi<Doctor> responseApi = this.doctorService.loadProfileByEmail(email);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}