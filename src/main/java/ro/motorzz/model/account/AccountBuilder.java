package ro.motorzz.model.account;

import java.util.Objects;

public class AccountBuilder {

    private Integer id;
    private String email;
    private String password;
    private AccountType type;
    private AccountStatus status;
    private Integer loginTimes;

    public Account build() {
        Objects.requireNonNull(id);
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(type);
        Objects.requireNonNull(status);
        Objects.requireNonNull(loginTimes);
        return new Account(id,email,password,type,status,loginTimes);
    }

    public AccountBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountBuilder setType(AccountType type) {
        this.type = type;
        return this;
    }

    public AccountBuilder setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public AccountBuilder setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
        return this;
    }
}
