package ro.motorzz.model.resetpassword;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ro.motorzz.core.validation.ValidPassword;

public class ResetPasswordJsonRequest implements ResetPasswordRequest{

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public ResetPasswordJsonRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public ResetPasswordJsonRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
