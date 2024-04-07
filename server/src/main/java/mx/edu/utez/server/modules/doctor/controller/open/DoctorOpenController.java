package mx.edu.utez.server.modules.doctor.controller.open;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/open/doctor")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class DoctorOpenController {
    Logger logger = LoggerFactory.getLogger(DoctorOpenController.class);
    private final DoctorService doctorService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Doctor>>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                             @RequestParam(defaultValue = "10", required = false) int size,
                                                             @RequestParam(defaultValue = "id", required = false) String sort,
                                                             @RequestParam(defaultValue = "asc", required = false) String direction,
                                                             @RequestBody(required = false) @Valid SearchDto searchDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Doctor>> responseApi = this.doctorService.openFindAll(searchDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Doctor>> findOne(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Doctor> responseApi = this.doctorService.openFindOne(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}
