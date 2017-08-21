package ro.motorzz.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import ro.motorzz.service.api.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Async
@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOG = Logger.getLogger(MailServiceImpl.class);
    private static final String REGISTRATION_CONFIRM_HTML = "registrationConfirm";
    private static final String NEW_PASSWORD_HTML = "newPassword";
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${properties.baseUrl}")
    private String baseUrl;

    @Override
    public void sendRegisterConfirmationEmail(String emailTo, String token) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("baseUrl", baseUrl);
        String htmlContent = templateEngine.process(REGISTRATION_CONFIRM_HTML, context);
        try {
            LOG.info("Sending register confirmation email to " + emailTo);
            configureMessage(message, emailTo, "Confirm account", htmlContent);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOG.error("Exception occurred while trying to send password change confirmation email", e);
        }
    }

    private void configureMessage(MimeMessageHelper message, String to, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        message.setFrom(new InternetAddress("fmarketapp@gmail.com", "Motorzz application"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content, true /* is html */);
    }

}
