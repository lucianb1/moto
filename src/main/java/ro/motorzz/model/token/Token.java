package ro.motorzz.model.token;

import java.time.LocalDateTime;

public class Token {

    protected String token;
    protected LocalDateTime expiresOn;

    public Token(String token, LocalDateTime expiresOn) {
        this.token = token;
        this.expiresOn = expiresOn;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }
}
