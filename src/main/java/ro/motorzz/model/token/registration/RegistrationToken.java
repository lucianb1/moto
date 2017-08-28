package ro.motorzz.model.token.registration;

import ro.motorzz.model.token.Token;

import java.time.LocalDateTime;

/**
 * Created by Luci on 22-Jun-17.
 */
public class RegistrationToken extends Token {

    private int accountID;

    public RegistrationToken(String token, LocalDateTime expiresOn, int accountID) {
        super(token,expiresOn);
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }
}
