package mx.edu.utez.server.modules.speciality.controller.open;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityDto;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityGroups;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.speciality.service.SpecialityService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/open/speciality/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class SpecialityOpenController {
    private final SpecialityService specialityService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Speciality>>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                                 @RequestParam(defaultValue = "10", required = false) int size,
                                                                 @RequestParam(defaultValue = "id", required = false) String sort,
                                                                 @RequestParam(defaultValue = "asc", required = false) String direction,
                                                                 @RequestBody(required = false) @Validated(SpecialityGroups.GetAll.class) SpecialityDto specialityDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Speciality>> responseApi = this.specialityService.openFindAll(specialityDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Speciality>> findOne(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Speciality> responseApi = this.specialityService.openFindOne(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseApi<Set<Speciality>>> listAll() {
        ResponseApi<Set<Speciality>> responseApi = this.specialityService.openListAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
