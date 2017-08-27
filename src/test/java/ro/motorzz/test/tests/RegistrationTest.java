package ro.motorzz.test.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.test.client.RegistrationClient;
import ro.motorzz.test.config.BaseTestClass;

public class RegistrationTest extends BaseTestClass{

	@Autowired
	private RegistrationClient registrationClient;



	@Test
	public void registrationTest(){
		String email = "m.mariuscornea@gmail.com";
		String password = randomString(10);
		RegistrationJsonRequest request = new RegistrationJsonRequest()
				.setEmail(email)
				.setPassword(password);
		registrationClient.register(request);
	}
}
