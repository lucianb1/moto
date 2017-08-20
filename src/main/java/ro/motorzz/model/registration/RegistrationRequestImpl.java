package ro.motorzz.model.registration;

/**
 * Created by Luci on 22-Jun-17.
 */
public class RegistrationRequestImpl implements RegistrationRequest {

    private final String email;
    private final String password;

    public RegistrationRequestImpl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
