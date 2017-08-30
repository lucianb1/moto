package ro.motorzz.test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.test.client.RegistrationControllerClient;
import ro.motorzz.test.config.BaseTestClass;


public class LoginAndLogoutTest extends BaseTestClass{

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationTest.class);

    @Autowired
    private  RegistrationTest registrationTest;

    @Autowired
    private  RegistrationControllerClient registrationControllerClient;

    @Test
    public void logoutTest(){
        String email = randomEmail();
        String password = randomString(10);
        LoginResponseJson loginResponseJson = registrationTest.registrationAndConfirmTest(email, password);
        registrationControllerClient.logout(loginResponseJson.getToken());
        //EdgeServerResponse<Void> voidEdgeServerResponse = registrationControllerClient.logoutRaw(loginResponseJson.getToken());
    }

    @Test
    public void loginTest(){
        String email = randomEmail();
        String password = randomString(10);
        LoginResponseJson loginResponseJson = registrationTest.registrationAndConfirmTest(email, password);
    }
    
}
