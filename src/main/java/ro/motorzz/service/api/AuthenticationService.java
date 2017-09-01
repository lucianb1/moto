package ro.motorzz.service.api;

import ro.motorzz.model.login.LoginRequest;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.security.PrincipalUser;

public interface AuthenticationService {

    LoginResponseJson login(LoginRequest loginRequest);

    LoginResponseJson confirmRegistration(String token);

    void logout(String token);

    PrincipalUser authenticateByToken(String token);

    LoginResponseJson confirmResetPassword(String token);
}
