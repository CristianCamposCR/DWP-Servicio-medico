package mx.edu.utez.server.modules.friendly_captcha.service;

import mx.edu.utez.server.modules.friendly_captcha.controller.dto.CaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CaptchaService {
    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${FRIENDLY_CAPTCHA.CAPTCHA_KEY}")
    private String captchaKey;
    @Value("${FRIENDLY_CAPTCHA.SITE_KEY}")
    private String siteKey;
    @Value("${FRIENDLY_CAPTCHA.URL_VERIFY_CAPTCHA}")
    private String urlVerifyCaptcha;

    public CaptchaResponse verifyCaptcha(String solution) {
        HttpHeaders headers = new HttpHeaders();
        // Set the content type of the request
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create the request body
        Map<String, String> requestBody = Map.of(
            "solution", solution,
            "secret", captchaKey,
            "sitekey", siteKey
        );
        // Send the request
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // Get the response
        ResponseEntity<CaptchaResponse> responseEntity = restTemplate.postForEntity(
            urlVerifyCaptcha, requestEntity, CaptchaResponse.class
        );

        return responseEntity.getBody();
    }
}
