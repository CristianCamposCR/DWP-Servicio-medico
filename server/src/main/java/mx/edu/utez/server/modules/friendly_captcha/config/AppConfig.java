package mx.edu.utez.server.modules.friendly_captcha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    // Component to send requests
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
