package ro.motorzz.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.model.resetpassword.ResetPasswordJsonRequest;
import ro.motorzz.model.token.authentication.AuthenticationToken;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.AuthenticationTokenRepository;
import ro.motorzz.test.client.RegistrationControllerClient;
import ro.motorzz.test.client.ResetPasswordControlerClient;
import ro.motorzz.test.edgeserver.EdgeServerResponse;
import ro.motorzz.test.mock.MailServiceMock;

@Component
public class AccountUtils {

    @Autowired
    private RegistrationControllerClient registrationControllerClient;

    @Autowired
    private MailServiceMock mailServiceMock;

    @Autowired
    private ResetPasswordControlerClient resetPasswordControlerClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    public void registration(String email, String password) {
        RegistrationJsonRequest request = new RegistrationJsonRequest()
                .setEmail(email)
                .setPassword(password);
        registrationControllerClient.register(request);
    }

    public LoginResponseJson registrationAndConfirm(String email, String password) {
        registration(email, password);
        String token = mailServiceMock.getRegisterTokenForEmail(email);
        final EdgeServerResponse<LoginResponseJson> edgeServerResponse = registrationControllerClient.confirmRegistration(token);
        return edgeServerResponse.getContent();
    }

    public String resetPasswordRequest(String email, String password, String newPassword) {
        registrationAndConfirm(email, password);
        ResetPasswordJsonRequest resetPasswordJsonRequest = new ResetPasswordJsonRequest()
                .setEmail(email)
                .setPassword(newPassword);
        resetPasswordControlerClient.resetPassword(resetPasswordJsonRequest);
        return mailServiceMock.getResetPasswordTokenForEmail(email);
    }

    public Account findAccountByEmail(String email){
        return accountRepository.findAccountByEmail(email);
    }

    public AuthenticationToken findAuthenticationToken(String token){
        return authenticationTokenRepository.findByToken(token);
    }
}
