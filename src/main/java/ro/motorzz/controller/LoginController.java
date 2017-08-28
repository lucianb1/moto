package ro.motorzz.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.motorzz.model.login.LoginJsonRequest;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.security.PrincipalUser;
import ro.motorzz.security.TokenAuthentication;
import ro.motorzz.service.api.AuthenticationService;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseJson login(@Valid @RequestBody LoginJsonRequest request) {
        return authenticationService.login(request);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(TokenAuthentication authentication, @AuthenticationPrincipal PrincipalUser principal) {
        System.out.println(principal);
        authenticationService.logout(authentication.getToken());
    }
}
