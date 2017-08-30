package ro.motorzz.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;
import ro.motorzz.service.api.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebSocketFilter extends GenericFilterBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationService authenticationService;


    //    @Autowired
    public WebSocketFilter(AuthenticationProvider authenticationProvider, AuthenticationEntryPoint entryPoint, AuthenticationService authenticationService) {
        LOGGER.info("Socket FILTER CONSTRUCTOR");
        this.authenticationProvider = authenticationProvider;
        this.entryPoint = entryPoint;
        this.authenticationService = authenticationService;
    }

    private void doTokenFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("DO FILTER Web Socket    = " + httpServletRequest.getQueryString());
        try {
            TokenAuthentication authentication = new TokenAuthentication();
            authentication.setToken("xxx");
            authentication.setAuthenticated(true);
            Authentication auth = this.authenticationProvider.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            this.entryPoint.commence(httpServletRequest, httpServletResponse, e);
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        doTokenFilter(servletRequest, servletResponse, chain);
    }
}
