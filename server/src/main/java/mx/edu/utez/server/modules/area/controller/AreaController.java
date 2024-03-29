package mx.edu.utez.server.modules.area.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.area.controller.dto.AreaDto;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.area.service.AreaService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import mx.edu.utez.server.utils.dtoGroups.GetAll;
import mx.edu.utez.server.utils.dtoGroups.Update;
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

import java.util.Set;

@RestController
@RequestMapping(value = "/api/management/area/")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;
    private final HashService hashService;

    @PostMapping("/paged/")
    public ResponseEntity<ResponseApi<Page<Area>>> findAll(Pageable pageable,
                                                           @RequestBody(required = false) @Validated(GetAll.class) AreaDto areaDto) {
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
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseApi<Set<Area>>> listAll() {
        ResponseApi<Set<Area>> responseApi = this.areaService.listAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @PostMapping("/")
    public ResponseEntity<ResponseApi<Area>> save(@RequestBody AreaDto areaDto) {
        try {
            ResponseApi<Area> responseApi = this.areaService.save(areaDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseApi<Area>> update(@RequestBody @Validated(Update.class) AreaDto areaDto) {
        try {
            areaDto.setId(this.hashService.decryptId(areaDto.getIdStr()));
            ResponseApi<Area> responseApi = this.areaService.update(areaDto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}