package ro.motorzz.test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.test.client.RegistrationControllerClient;
import ro.motorzz.test.config.BaseTestClass;
import ro.motorzz.test.edgeserver.EdgeServerResponse;
import ro.motorzz.test.mock.MailServiceMock;

@Component
public class RegistrationTest extends BaseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationTest.class);

    @Autowired
    private RegistrationControllerClient registrationControllerClient;

    @Autowired
    private MailServiceMock mailServiceMock;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void registrationTest(String email, String password) {
        RegistrationJsonRequest request = new RegistrationJsonRequest()
                .setEmail(email)
                .setPassword(password);
        registrationControllerClient.register(request);
    }

    @Test(dataProvider = "email-and-password-data-provider")
    public LoginResponseJson registrationAndConfirmTest(String email, String password) {
        long millis = System.currentTimeMillis();
        this.registrationTest(email, password);
        String token = mailServiceMock.getRegisterTokenForEmail(email);
        final EdgeServerResponse<LoginResponseJson> edgeServerResponse = registrationControllerClient.confirmRegistration(token);
        Account account = accountRepository.findAccountByEmail(email);
        Assert.assertEquals(account.getEmail(), edgeServerResponse.getContent().getEmail());
        Assert.assertEquals(account.getStatus(), AccountStatus.ACTIVE);
        LOGGER.info("DURATION:     " + (System.currentTimeMillis() - millis));
        return edgeServerResponse.getContent();
    }

    @DataProvider(name = "email-and-password-data-provider")
    private Object[][] emailsDataProvider() {
        return new Object[][]{
                {randomEmail(), randomString(10)}
        };
    }
}
