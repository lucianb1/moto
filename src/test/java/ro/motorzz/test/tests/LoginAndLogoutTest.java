package ro.motorzz.test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.test.client.RegistrationControllerClient;
import ro.motorzz.test.config.BaseTestClass;
import ro.motorzz.test.utils.AccountUtils;


public class LoginAndLogoutTest extends BaseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationTest.class);

    @Autowired
    private RegistrationControllerClient registrationControllerClient;

    @Autowired
    private AccountUtils accountUtils;

    @Test(expectedExceptions = NotFoundException.class)
    public void logoutTest() {
        String email = randomEmail();
        String password = randomString(10);
        LoginResponseJson loginResponseJson = accountUtils.registrationAndConfirm(email, password);
        registrationControllerClient.logout(loginResponseJson.getToken());
        accountUtils.findAuthenticationToken(loginResponseJson.getToken());
    }

    @Test
    public void loginTest() {
        String email = randomEmail();
        String password = randomString(10);
        LoginResponseJson loginResponseJson = accountUtils.registrationAndConfirm(email, password);
        accountUtils.findAuthenticationToken(loginResponseJson.getToken());
    }

}
