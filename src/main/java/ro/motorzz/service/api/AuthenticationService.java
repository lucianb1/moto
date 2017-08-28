package ro.motorzz.service.api;

import ro.motorzz.model.login.LoginRequest;
import ro.motorzz.model.login.response.LoginResponseJson;

public interface AuthenticationService {

    LoginResponseJson login(LoginRequest loginRequest);

    LoginResponseJson confirmRegistration(String token);

    void logout(String token);
}
