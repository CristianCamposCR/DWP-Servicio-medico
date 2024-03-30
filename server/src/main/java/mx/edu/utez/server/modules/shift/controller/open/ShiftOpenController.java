package mx.edu.utez.server.modules.shift.controller.open;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.shift.service.ShiftService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/open/shift")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ShiftOpenController {
    private final ShiftService shiftService;

    @GetMapping("/")
    public ResponseEntity<ResponseApi<Set<Shift>>> listAll() {
        ResponseApi<Set<Shift>> responseApi = this.shiftService.openListAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
