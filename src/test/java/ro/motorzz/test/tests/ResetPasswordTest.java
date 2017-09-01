package ro.motorzz.test.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.Test;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.resetpassword.ResetPasswordJsonRequest;
import ro.motorzz.repository.ResetPasswordTokenRepository;
import ro.motorzz.test.client.ResetPasswordControlerClient;
import ro.motorzz.test.config.BaseTestClass;
import ro.motorzz.test.mock.MailServiceMock;
import ro.motorzz.test.utils.AccountUtils;

public class ResetPasswordTest extends BaseTestClass {

    @Autowired
    private ResetPasswordControlerClient resetPasswordControlerClient;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private MailServiceMock mailServiceMock;

    @Test
    public void resetPasswordRequestTest() {
        String email = randomEmail();
        String password = randomString(10);
        String newPassword = randomString(10);
        accountUtils.registrationAndConfirm(email, password);
        ResetPasswordJsonRequest resetPasswordJsonRequest = new ResetPasswordJsonRequest()
                .setEmail(email)
                .setPassword(newPassword);
        resetPasswordControlerClient.resetPassword(resetPasswordJsonRequest);
        String token = mailServiceMock.getResetPasswordTokenForEmail(email);
        resetPasswordTokenRepository.findByToken(token);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void confirmResetPasswordTest() {
        String email = randomEmail();
        String password = randomString(10);
        String newPassword = randomString(10);
        String token = accountUtils.resetPasswordRequest(email, password, newPassword);
        resetPasswordControlerClient.confirmResetPassword(token);
        Account account = accountUtils.findAccountByEmail(email);
        Assert.assertEquals(passwordEncoder.matches(newPassword, account.getPassword()), true);
        resetPasswordTokenRepository.findByToken(token); //token is removed from db
    }
}
