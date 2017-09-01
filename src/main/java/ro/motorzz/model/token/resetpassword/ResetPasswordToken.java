package ro.motorzz.model.token.resetpassword;

import ro.motorzz.model.token.Token;

import java.time.LocalDateTime;

public class ResetPasswordToken extends Token{

    private final String password;
    private final int accountId;

    public ResetPasswordToken(String token, LocalDateTime expiresOn, String password, int accountId) {
        super(token, expiresOn);
        this.password = password;
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountId() {
        return accountId;
    }
}
