package ro.motorzz.service;

import ro.motorzz.service.api.MailService;

public class ConsoleMailService implements MailService {

    @Override
    public void sendRegisterConfirmationEmail(String emailTo, String token) {
        System.out.println(String.format("Registration token for %s = %s", emailTo, token));
    }

    @Override
    public void sendPasswordConfirmationEmail(String emailTo, String token) {

    }
}
