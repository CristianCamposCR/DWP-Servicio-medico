package mx.edu.utez.server.modules.speciality.controller.management;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityDto;
import mx.edu.utez.server.modules.speciality.controller.dto.SpecialityGroups;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.speciality.service.SpecialityService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/management/speciality")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class SpecialityManagementController {
    private final SpecialityService specialityService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Speciality>>> findAll(Pageable pageable,
                                                                 @RequestBody(required = false) @Validated(SpecialityGroups.GetAll.class) SpecialityDto specialityDto) {
        ResponseApi<Page<Speciality>> responseApi = this.specialityService.findAll(specialityDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Speciality>> findOne(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Speciality> responseApi = this.specialityService.findOne(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Speciality>> save(@RequestBody @Validated(SpecialityGroups.Save.class) SpecialityDto specialityDto) {
        try {
            ResponseApi<Speciality> responseApi = this.specialityService.save(specialityDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApi<Speciality>> update(@RequestBody @Validated(SpecialityGroups.Update.class) SpecialityDto specialityDto) {
        try {
            ResponseApi<Speciality> responseApi = this.specialityService.update(specialityDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseApi<Speciality>> changeStatus(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Speciality> responseApi = this.specialityService.changeStatus(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}