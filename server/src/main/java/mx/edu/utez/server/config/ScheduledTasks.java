package mx.edu.utez.server.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.verification_code.service.VerificationCodeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final VerificationCodeService verificationCodeService;

    @Scheduled(cron = "0 */2 * * * *")
    public void setVerificationCodeExpiration() {
        this.verificationCodeService.updateExpiration();
    }
}
