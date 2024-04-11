package mx.edu.utez.server.modules.appointment.controller.user;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.appointment.controller.dto.CheckAvailabilityDto;
import mx.edu.utez.server.modules.appointment.controller.dto.RescheduleDto;
import mx.edu.utez.server.modules.appointment.controller.dto.SaveAppointmentDto;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.service.AppointmentService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.SearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/appointment")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
public class AppointmentController {
    Logger logger = LoggerFactory.getLogger(AppointmentController.class);
    private final AppointmentService appointmentService;
    private final HashService hashService;

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/check-availability/")
    public ResponseEntity<ResponseApi<Boolean>> checkAvailability(@Valid @RequestBody CheckAvailabilityDto dto) {
        if (this.appointmentService.isAvailable(dto.getSpeciality().getId(), dto.getShift().getId(), dto.getScheduledAt()))
            return ResponseEntity.ok(new ResponseApi<>(true, HttpStatus.OK, false, "Hay disponibilidad."));
        return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.NO_AVAILABILITY.name()));
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/patient/pending/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllPendingByPatient(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(defaultValue = "10", required = false) int size,
                                                                                  @RequestParam(defaultValue = "id", required = false) String sort,
                                                                                  @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                                  @RequestBody(required = false) @Valid SearchDto searchDto) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllPendingByPatient(searchDto, username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/patient/active/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllActiveByPatient(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(defaultValue = "10", required = false) int size,
                                                                                 @RequestParam(defaultValue = "id", required = false) String sort,
                                                                                 @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                                 @RequestBody(required = false) @Valid SearchDto searchDto) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllActiveByPatient(searchDto, username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/patient/history/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllHistoryByPatient(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(defaultValue = "10", required = false) int size,
                                                                                  @RequestParam(defaultValue = "id", required = false) String sort,
                                                                                  @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                                  @RequestBody(required = false) @Valid SearchDto searchDto) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllHistoryByPatient(searchDto, username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/doctor/assigned/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllAssignedByDoctor(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(defaultValue = "10", required = false) int size,
                                                                                  @RequestParam(defaultValue = "id", required = false) String sort,
                                                                                  @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                                  @RequestBody(required = false) @Valid SearchDto searchDto) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllAssignedByDoctor(searchDto, username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/doctor/history/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllHistoryByDoctor(@RequestParam(defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(defaultValue = "10", required = false) int size,
                                                                                 @RequestParam(defaultValue = "id", required = false) String sort,
                                                                                 @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                                 @RequestBody(required = false) @Valid SearchDto searchDto) {
        String username = Methods.getLoggedUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllHistoryByDoctor(searchDto, username, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/")
    public ResponseEntity<ResponseApi<Boolean>> save(@Valid @RequestBody SaveAppointmentDto dto) {
        try {
            ResponseApi<Boolean> responseApi = this.appointmentService.save(dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST, true, e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name()));
        }
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/reschedule/{id}")
    public ResponseEntity<ResponseApi<Boolean>> reschedule(@PathVariable("id") String encryptedId,
                                                           @Valid @RequestBody RescheduleDto dto) {
        try {
            Long id = hashService.decryptId(encryptedId);
            String username = Methods.getLoggedUsername();
            ResponseApi<Boolean> responseApi = this.appointmentService.rescheduleAppointment(id, username, dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST, true, e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name()));
        }
    }
}