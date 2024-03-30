package mx.edu.utez.server.modules.hashing.controller;

import lombok.AllArgsConstructor;
import mx.edu.utez.server.utils.HashService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hashing/")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class HashingController {
    private HashService hashService;

    @PostMapping("/encrypt")
    public String encryptData(@RequestBody String data) {
        return hashService.encrypt(data);
    }

    @PostMapping("/decrypt")
    public String decryptData(@RequestBody String encryptedData) {
        return hashService.decrypt(encryptedData);
    }
}