package ro.motorzz.test.mock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.motorzz.service.api.MailService;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class MailServiceMock  implements MailService{

    private Map<String, String> confirmationMails = new HashMap<>(); //email / token

    @Override
    public void sendRegisterConfirmationEmail(String emailTo, String token) {
        confirmationMails.put(emailTo,token);
    }

    public String getToken(String email) {
        return confirmationMails.get(email);
    }
}
