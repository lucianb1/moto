package ro.motorzz.model.registration;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ro.motorzz.core.validation.ValidPassword;

public class RegistrationJsonRequest implements RegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public RegistrationJsonRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegistrationJsonRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
