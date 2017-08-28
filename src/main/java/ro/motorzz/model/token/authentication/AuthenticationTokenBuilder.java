package ro.motorzz.model.token.authentication;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuthenticationTokenBuilder {

    private String token;
    private LocalDateTime expiresOn;
    private Integer accountID;

    public AuthenticationToken build(){
        Objects.requireNonNull(token);
        Objects.requireNonNull(expiresOn);
        Objects.requireNonNull(accountID);
        return new AuthenticationToken(token,expiresOn,accountID);
    }

    public AuthenticationTokenBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthenticationTokenBuilder setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public AuthenticationTokenBuilder setAccountID(Integer accountID) {
        this.accountID = accountID;
        return this;
    }
}
