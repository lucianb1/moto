package ro.motorzz.service.api;

public interface MailService {
    void sendRegisterConfirmationEmail(String emailTo, String token);
}
