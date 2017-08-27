package ro.motorzz.model.token;

import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrationTokenBuilder {

    private String token;
    private LocalDateTime expiresOn;
    private Integer accountID;

    public RegistrationToken build(){
        Objects.requireNonNull(token);
//        Objects.requireNonNull(expiresOn);
        Objects.requireNonNull(accountID);
        return new RegistrationToken(token,expiresOn,accountID);
    }

    public RegistrationTokenBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public RegistrationTokenBuilder setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public RegistrationTokenBuilder setAccountID(Integer accountID) {
        this.accountID = accountID;
        return this;
    }
}
