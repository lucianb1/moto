/**
 *
 */
package ro.motorzz.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication implements Authentication {
    private static final long serialVersionUID = -7267273228248812405L;

    private PrincipalUser principal;
    private boolean authenticated;
    private String token;


    @Override
    public String getName() {
        return principal.getEmail();
    }

    @Override
    public PrincipalUser getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    public TokenAuthentication setPrincipal(PrincipalUser principal) {
        this.principal = principal;
        return this;
    }

    public String getToken() {
        return token;
    }

    public TokenAuthentication setToken(String token) {
        this.token = token;
        return this;
    }
}
