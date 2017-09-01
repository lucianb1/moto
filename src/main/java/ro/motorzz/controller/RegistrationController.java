package ro.motorzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.service.api.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /**
     * 409 - multiple consecutive registration
     * 412 - account status is not pending
     * 200 - success
     */
    @RequestMapping(method = RequestMethod.POST)
    public void registerAccount(@Valid @RequestBody RegistrationJsonRequest request) {
        registrationService.register(request);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public LoginResponseJson confirmRegistration(@RequestParam String token) {
        return registrationService.confirmRegistration(token);
    }


}
