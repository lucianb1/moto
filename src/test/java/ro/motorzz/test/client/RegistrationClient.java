package ro.motorzz.test.client;

import org.springframework.stereotype.Component;
import org.testng.Assert;

import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.test.mock.EdgeServerRequest;
import ro.motorzz.test.mock.EdgeServerRequestBuilder;
import ro.motorzz.test.mock.EdgeServerRequestMethod;
import ro.motorzz.test.mock.EdgeServerResponse;

@Component
public class RegistrationClient extends BaseControllerClient{

	public void register(RegistrationJsonRequest request){
		EdgeServerRequest serverRequest = new EdgeServerRequestBuilder()
			.setUrl("/registration")
			.setBody(request)
			.setMethod(EdgeServerRequestMethod.POST)
			.build();
		
		EdgeServerResponse<Void> edgeServerResponse = edgeServer.executeRequest(serverRequest, Void.class);
		
		Assert.assertEquals(edgeServerResponse.getStatus(), 200);
	}
}
