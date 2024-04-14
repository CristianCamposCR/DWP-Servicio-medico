package mx.edu.utez.server.modules.record.controller.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.record.controller.dto.RecordDto;
import mx.edu.utez.server.modules.record.controller.dto.RecordGroups;
import mx.edu.utez.server.modules.record.service.RecordService;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/record")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('DOCTOR')")
public class RecordController {
    Logger logger = LoggerFactory.getLogger(RecordController.class);
    private final RecordService recordService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/")
    public ResponseEntity<ResponseApi<Boolean>> save(@Validated(RecordGroups.Save.class) @RequestBody RecordDto dto) {
        try {
            ResponseApi<Boolean> responseApi = recordService.save(dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name()));
        }
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/")
    public ResponseEntity<ResponseApi<Boolean>> update(@Validated(RecordGroups.Update.class) @RequestBody RecordDto dto) {
        try {
            ResponseApi<Boolean> responseApi = recordService.update(dto);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name()));
        }
    }
}
