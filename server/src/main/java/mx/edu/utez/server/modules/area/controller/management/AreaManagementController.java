package mx.edu.utez.server.modules.area.controller.management;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.area.controller.dto.AreaDto;
import mx.edu.utez.server.modules.area.controller.dto.AreaGroups;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.area.service.AreaService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/management/area/")
@CrossOrigin(origins = {"*"})
@PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
@RequiredArgsConstructor
public class AreaManagementController {
    Logger logger = LoggerFactory.getLogger(AreaManagementController.class);
    private final AreaService areaService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Area>>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                           @RequestParam(defaultValue = "10", required = false) int size,
                                                           @RequestParam(defaultValue = "id", required = false) String sort,
                                                           @RequestParam(defaultValue = "asc", required = false) String direction,
                                                           @RequestBody(required = false) @Validated(AreaGroups.GetAll.class) AreaDto areaDto) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<Area>> responseApi = this.areaService.findAll(areaDto, pageable);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Area>> findOne(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Area> responseApi = this.areaService.findOne(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Area>> save(@RequestBody @Validated(AreaGroups.Save.class) AreaDto areaDto) {
        try {
            ResponseApi<Area> responseApi = this.areaService.save(areaDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApi<Area>> update(@RequestBody @Validated(AreaGroups.Update.class) AreaDto areaDto) {
        try {
            ResponseApi<Area> responseApi = this.areaService.update(areaDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseApi<Area>> changeStatus(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<Area> responseApi = this.areaService.changeStatus(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}