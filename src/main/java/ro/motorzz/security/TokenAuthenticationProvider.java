/**
 *
 */
package ro.motorzz.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ro.motorzz.service.api.AuthenticationService;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public TokenAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }

}
