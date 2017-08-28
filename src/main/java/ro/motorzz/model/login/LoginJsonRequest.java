package ro.motorzz.model.login;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ro.motorzz.core.validation.ValidPassword;

public class LoginJsonRequest implements  LoginRequest{

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
