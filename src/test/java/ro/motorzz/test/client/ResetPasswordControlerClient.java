package ro.motorzz.test.client;

import org.springframework.stereotype.Component;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.resetpassword.ResetPasswordJsonRequest;
import ro.motorzz.model.resetpassword.ResetPasswordRequest;
import ro.motorzz.test.edgeserver.*;
import ro.motorzz.test.utils.AssertUtils;

@Component
public class ResetPasswordControlerClient extends BaseControllerClient {

    public EdgeServerResponse<Void> resetPasswordRaw(ResetPasswordRequest request) {
        EdgeServerRequest serverRequest = new EdgeServerRequestBuilder()
                .setUrl("/reset-password")
                .setBody(request)
                .setMethod(EdgeServerRequestMethod.POST)
                .build();
        return edgeServer.executeRequest(serverRequest, Void.class);
    }

    public void resetPassword(ResetPasswordJsonRequest request) {
        EdgeServerResponse<Void> edgeServerResponse = resetPasswordRaw(request);
        AssertUtils.assertIsSuccessful(edgeServerResponse);
    }

    public EdgeServerResponse<LoginResponseJson> confirmResetPasswordRaw(String token) {
        EdgeServerRequest serverRequest = new EdgeServerRequestBuilder()
                .setUrl("/reset-password/confirm")
                .addParam("token", token)
                .setMethod(EdgeServerRequestMethod.GET)
                .build();
        return edgeServer.executeRequest(serverRequest, LoginResponseJson.class);
    }

    public EdgeServerResponse<LoginResponseJson> confirmResetPassword(String token) {
        EdgeServerResponse<LoginResponseJson> edgeServerResponse = confirmResetPasswordRaw(token);
        AssertUtils.assertIsSuccessful(edgeServerResponse);
        return edgeServerResponse;
    }
}
