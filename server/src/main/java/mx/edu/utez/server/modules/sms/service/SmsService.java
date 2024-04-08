package mx.edu.utez.server.modules.sms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import mx.edu.utez.server.modules.sms.controller.dto.SmsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsService {
    @Value("${TWILIO_ACCOUNT_SID}")
    String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    String token;
    @Value("${TWILIO_NUMBER}")
    String phoneNumber;

    @Transactional(readOnly = true)
    public boolean sendSms(SmsDto dto) {
        try {
            Twilio.init(sid, token);
            Message.creator(new PhoneNumber("+527772002582"), new PhoneNumber(phoneNumber), dto.getBody()).create();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
