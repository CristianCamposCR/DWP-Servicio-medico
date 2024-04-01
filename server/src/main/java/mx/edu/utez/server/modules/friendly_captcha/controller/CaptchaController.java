package mx.edu.utez.server.modules.friendly_captcha.controller;

import mx.edu.utez.server.modules.friendly_captcha.controller.dto.CaptchaResponse;
import mx.edu.utez.server.modules.friendly_captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)
@RequestMapping("/api/captcha")
public class CaptchaController {

        private final CaptchaService captchaService;
        @Autowired
        public CaptchaController(CaptchaService captchaService) {
            this.captchaService = captchaService;
        }

        @PostMapping("/verify-captcha")
        public CaptchaResponse verifyCaptcha(@RequestBody(required = true) Map<String, String> solution) {
            return captchaService.verifyCaptcha(solution.get("solution"));
        }
}
