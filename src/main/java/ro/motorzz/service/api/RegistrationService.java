package ro.motorzz.service.api;

import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationRequest;

public interface RegistrationService {

    void register(RegistrationRequest request);

    LoginResponseJson confirmRegistration(String token);

    void resendRegistrationToken(String email);
}
