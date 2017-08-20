package ro.motorzz.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.error("Authentication exception", authException);
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Get the printwriter object from response to write the required json object to the output stream
        final PrintWriter out = response.getWriter();
        // Assuming your json object is **jsonObject**, perform the following, it will return your json object
        String errorCode = "access.denied";
        if (authException instanceof CredentialsExpiredException) {
            errorCode = "token.expired";
        }
        System.out.println("AUTHENTICATION EXCEPTION!!");
        out.print("Authentication exception"); //TODO fix this
        out.flush();
    }

}