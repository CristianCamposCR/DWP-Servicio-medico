package mx.edu.utez.server.modules.gender.controller.open;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.gender.service.GenderService;
import mx.edu.utez.server.utils.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/open/gender")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class GenderOpenController {
    private final GenderService genderService;

    @GetMapping("/")
    public ResponseEntity<ResponseApi<Set<Gender>>> listAll() {
        ResponseApi<Set<Gender>> responseApi = this.genderService.openListAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}