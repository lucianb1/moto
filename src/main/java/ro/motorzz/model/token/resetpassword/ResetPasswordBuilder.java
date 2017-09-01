package ro.motorzz.model.token.resetpassword;

import java.time.LocalDateTime;
import java.util.Objects;

public class ResetPasswordBuilder {

    public String token;
    public LocalDateTime expiresOn;
    public String password;
    public int accountID;

    public ResetPasswordToken build() {
        Objects.requireNonNull(token);
        Objects.requireNonNull(expiresOn);
        Objects.requireNonNull(password);
        Objects.requireNonNull(accountID);
        return new ResetPasswordToken(token, expiresOn, password, accountID);
    }

    public ResetPasswordBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public ResetPasswordBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public ResetPasswordBuilder setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public ResetPasswordBuilder setAccountID(int accountID) {
        this.accountID = accountID;
        return this;
    }
}
