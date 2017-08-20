package ro.motorzz.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ro.motorzz.model.account.AccountType;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Luci on 21-Jun-17.
 */
public class PrincipalUser extends User {

    public PrincipalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
    }

    @JsonCreator
    public static PrincipalUser fromJson(
            @JsonProperty("email") String email,
            @JsonProperty("accountType") String stringAccountType) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(stringAccountType);
        AccountType accountType = AccountType.valueFromString(stringAccountType);
        return new PrincipalUser(email, "", Collections.singleton(new SimpleGrantedAuthority(accountType.name())));
    }

    @JsonIgnore
    private int id;
    private String email;
    private String password;
    private AccountType accountType;
    private boolean firstLogin;


    public int getAccountID() {
        return id;
    }

    public PrincipalUser setId(int id) {
        this.id = id;
        return this;
    }


    public PrincipalUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public PrincipalUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public PrincipalUser setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public PrincipalUser setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }
}
