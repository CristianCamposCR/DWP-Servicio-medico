package mx.edu.utez.server.modules.appointment_type.controller.open;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.appointment_type.model.AppointmentType;
import mx.edu.utez.server.modules.appointment_type.service.AppointmentTypeService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/open/appointment-type")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AppointmentTypeOpenController {
    private final AppointmentTypeService appointmentTypeService;

    @GetMapping("/")
    public ResponseEntity<ResponseApi<Set<AppointmentType>>> listAll() {
        ResponseApi<Set<AppointmentType>> responseApi = this.appointmentTypeService.openListAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}