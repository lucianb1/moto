package ro.motorzz.model.registration;

import java.util.Objects;

/**
 * Created by Luci on 22-Jun-17.
 */
public class RegistrationRequestBuilder {

    private String email;
    private String password;

    public RegistrationRequestImpl build() {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        return new RegistrationRequestImpl(email, password);
    }

    public RegistrationRequestBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegistrationRequestBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

}
