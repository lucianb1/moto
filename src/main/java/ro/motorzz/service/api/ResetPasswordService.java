package ro.motorzz.service.api;

import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.resetpassword.ResetPasswordRequest;

public interface ResetPasswordService {

    void resetPasswordRequest(ResetPasswordRequest resetPasswordRequest);

    LoginResponseJson confirmResetPassword(String token);
}
