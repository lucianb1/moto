package ro.motorzz.test.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.model.registration.RegistrationRequestBuilder;
import ro.motorzz.model.registration.RegistrationRequestImpl;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.test.client.RegistrationClient;
import ro.motorzz.test.config.BaseTestClass;
import ro.motorzz.test.edgeserver.EdgeServerResponse;
import ro.motorzz.test.mock.MailServiceMock;

public class RegistrationTest extends BaseTestClass{

	@Autowired
	private RegistrationClient registrationClient;

	@Autowired
	private MailServiceMock mailServiceMock;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void registrationTest(){
		String email = "email@yahoo.com";
		String password = randomString(10);
		RegistrationJsonRequest request = new RegistrationJsonRequest()
				.setEmail(email)
				.setPassword(password);
		registrationClient.register(request);
	}

	@Test
	public void registrationAndConfirmTest(){
		long millis = System.currentTimeMillis();
		String email = BaseTestClass.randomEmail();
		String password = RandomStringUtils.randomAlphanumeric(10);
		RegistrationRequestImpl registrationRequest = new RegistrationRequestBuilder()
				.setEmail(email)
				.setPassword(password)
				.build();
		registrationClient.register(registrationRequest);
		String token = mailServiceMock.getRegisterTokenForEmail(email);
		final EdgeServerResponse<LoginResponseJson> edgeServerResponse = registrationClient.confirmRegistration(token);
		Account account = accountRepository.findAccountByEmail(email);
		Assert.assertEquals(account.getEmail(), edgeServerResponse.getContent().getEmail());
		Assert.assertEquals(account.getStatus(), AccountStatus.ACTIVE);
		System.out.println("DURATION:     " + (System.currentTimeMillis() - millis));

	}
}
