package ro.motorzz.test.client;

import org.springframework.stereotype.Component;
import ro.motorzz.model.login.LoginJsonRequest;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationRequest;
import ro.motorzz.test.edgeserver.EdgeServerRequest;
import ro.motorzz.test.edgeserver.EdgeServerRequestBuilder;
import ro.motorzz.test.edgeserver.EdgeServerRequestMethod;
import ro.motorzz.test.edgeserver.EdgeServerResponse;
import ro.motorzz.test.utils.AssertUtils;

@Component
public class RegistrationControllerClient extends BaseControllerClient{

	public EdgeServerResponse<Void> registerRaw(RegistrationRequest request) {
		EdgeServerRequest serverRequest = new EdgeServerRequestBuilder()
				.setUrl("/registration")
				.setBody(request)
				.setMethod(EdgeServerRequestMethod.POST)
				.build();
		return edgeServer.executeRequest(serverRequest, Void.class);
	}

	public void register(RegistrationRequest request){
		EdgeServerResponse<Void> response = this.registerRaw(request);
		AssertUtils.assertIsSuccessful(response);
	}

	public EdgeServerResponse<LoginResponseJson> confirmRegistrationRaw(String token){
		EdgeServerRequest edgeServerRequest = new EdgeServerRequestBuilder()
				.setUrl("/registration/confirm")
				.setMethod(EdgeServerRequestMethod.GET)
				.addParam("token",token)
				.build();
		return edgeServer.executeRequest(edgeServerRequest, LoginResponseJson.class);
	}

	public EdgeServerResponse<LoginResponseJson> confirmRegistration(String token){
		EdgeServerResponse<LoginResponseJson> response = this.confirmRegistrationRaw(token);
		AssertUtils.assertIsSuccessful(response);
		return response;
	}

	public EdgeServerResponse<Void> logoutRaw(String token){
		EdgeServerRequest edgeServerRequest = new EdgeServerRequestBuilder()
				.setUrl("/logout")
				.setMethod(EdgeServerRequestMethod.POST)
				.setToken(token)
				.build();
		return edgeServer.executeRequest(edgeServerRequest, Void.class);
	}

	public EdgeServerResponse<Void> logout(String token){
		EdgeServerResponse<Void> response = this.logoutRaw(token);
		AssertUtils.assertIsSuccessful(response);
		return response;
	}

	private EdgeServerResponse<LoginResponseJson> loginRaw(LoginJsonRequest request){
		EdgeServerRequest edgeServerRequest = new EdgeServerRequestBuilder()
				.setUrl("/login")
				.setMethod(EdgeServerRequestMethod.POST)
				.setBody(request)
				.build();
		return edgeServer.executeRequest(edgeServerRequest, LoginResponseJson.class);
	}

	public EdgeServerResponse<LoginResponseJson> login(LoginJsonRequest request){
		EdgeServerResponse<LoginResponseJson> response = this.loginRaw(request);
		AssertUtils.assertIsSuccessful(response);
		return response;

	}
}
