package mx.edu.utez.server.modules.friendly_captcha.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CaptchaResponse {
    private boolean success;
    private List<String> errors;
}
