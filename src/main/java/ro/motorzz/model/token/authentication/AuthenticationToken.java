package ro.motorzz.model.token.authentication;

import ro.motorzz.model.token.Token;

import java.time.LocalDateTime;

public class AuthenticationToken extends Token {

    private int accoutnID;

    public AuthenticationToken(String token, LocalDateTime expiresOn, int accountID) {
        super(token, expiresOn);
        this.accoutnID = accountID;
    }

    public int getAccoutnID() {
        return accoutnID;
    }
}
