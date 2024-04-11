package mx.edu.utez.server.modules.appointment.controller.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointment.service.AppointmentService;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.SearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/appointment/management")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AppointmentManagementController {
    Logger logger = LoggerFactory.getLogger(AppointmentManagementController.class);
    private final AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/pending/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllPending(@RequestParam(defaultValue = "0", required = false) int page,
                                                                         @RequestParam(defaultValue = "10", required = false) int size,
                                                                         @RequestParam(defaultValue = "id", required = false) String sort,
                                                                         @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                         @RequestBody(required = false) @Valid SearchDto searchDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllPending(searchDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/active/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllActive(@RequestParam(defaultValue = "0", required = false) int page,
                                                                        @RequestParam(defaultValue = "10", required = false) int size,
                                                                        @RequestParam(defaultValue = "id", required = false) String sort,
                                                                        @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                        @RequestBody(required = false) @Valid SearchDto searchDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllActive(searchDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/history/paged/")
    public ResponseEntity<ResponseApi<Page<Appointment>>> findAllHistory(@RequestParam(defaultValue = "0", required = false) int page,
                                                                         @RequestParam(defaultValue = "10", required = false) int size,
                                                                         @RequestParam(defaultValue = "id", required = false) String sort,
                                                                         @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                         @RequestBody(required = false) @Valid SearchDto searchDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Appointment>> responseApi = this.appointmentService.findAllHistory(searchDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}