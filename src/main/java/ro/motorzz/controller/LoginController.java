package ro.motorzz.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.motorzz.model.login.LoginJsonRequest;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.security.TokenAuthentication;
import ro.motorzz.service.api.AuthenticationService;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * @return 404 - email not found
     * 401 - password didn't match
     * 412 - account is not active
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseJson login(@Valid @RequestBody LoginJsonRequest request) {
        return authenticationService.login(request);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(TokenAuthentication authentication) {
        authenticationService.logout(authentication.getToken());
        SecurityContextHolder.clearContext();
    }
}
