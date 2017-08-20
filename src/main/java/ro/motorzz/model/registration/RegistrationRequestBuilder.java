package ro.motorzz.model.registration;

/**
 * Created by Luci on 22-Jun-17.
 */
public class RegistrationRequestBuilder {

    private String email;
    private String password;

    public RegistrationRequestImpl build() {
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
