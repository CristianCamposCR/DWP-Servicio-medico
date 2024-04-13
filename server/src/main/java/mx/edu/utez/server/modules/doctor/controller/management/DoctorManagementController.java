package mx.edu.utez.server.modules.doctor.controller.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.OptionsDto;
import mx.edu.utez.server.modules.appointment.controller.dto.CheckAvailabilityDto;
import mx.edu.utez.server.modules.doctor.controller.dto.DoctorDto;
import mx.edu.utez.server.modules.doctor.controller.dto.GetAvailableHoursDto;
import mx.edu.utez.server.modules.doctor.controller.dto.IDoctorListView;
import mx.edu.utez.server.modules.doctor.controller.dto.UpdateDoctorDto;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.doctor.service.DoctorService;
import mx.edu.utez.server.utils.HashService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/management/doctor")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class DoctorManagementController {
    Logger logger = LoggerFactory.getLogger(DoctorManagementController.class);
    private final DoctorService doctorService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Doctor>>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                             @RequestParam(defaultValue = "10", required = false) int size,
                                                             @RequestParam(defaultValue = "id", required = false) String sort,
                                                             @RequestParam(defaultValue = "asc", required = false) String direction,
                                                             @RequestBody(required = false) @Valid SearchDto searchDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Doctor>> responseApi = this.doctorService.findAll(searchDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Doctor>> findOne(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Doctor> responseApi = this.doctorService.findOne(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Doctor>> save(@RequestBody @Valid DoctorDto doctorDto) {
        try {
            ResponseApi<Doctor> responseApi = this.doctorService.save(doctorDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApi<Doctor>> update(@RequestBody @Valid UpdateDoctorDto updateDoctorDto) {
        try {
            ResponseApi<Doctor> responseApi = this.doctorService.update(updateDoctorDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/available/")
    public ResponseEntity<ResponseApi<Set<IDoctorListView>>> findAllAvailableDoctors(
            @Validated(CheckAvailabilityDto.Assigning.class) @RequestBody CheckAvailabilityDto dto) {
        try {
            ResponseApi<Set<IDoctorListView>> responseApi = this.doctorService.findAllAvailable(dto, false);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/aux/available/")
    public ResponseEntity<ResponseApi<Set<IDoctorListView>>> findAllAvailableAuxDoctors(
            @Validated(CheckAvailabilityDto.Assigning.class) @RequestBody CheckAvailabilityDto dto) {
        try {
            ResponseApi<Set<IDoctorListView>> responseApi = this.doctorService.findAllAvailable(dto, true);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/available-hours/")
    public ResponseEntity<ResponseApi<List<OptionsDto<Integer>>>> getAvailableHours(@Valid @RequestBody GetAvailableHoursDto dto) {
        try {
            ResponseApi<List<OptionsDto<Integer>>> responseApi = this.doctorService.getAvailableHours(dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}