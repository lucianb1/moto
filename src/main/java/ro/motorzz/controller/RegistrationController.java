package ro.motorzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.motorzz.model.registration.RegistrationJsonRequest;
import ro.motorzz.service.api.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.POST)
    public void registerAccount(@Valid @RequestBody RegistrationJsonRequest request) {
        registrationService.register(request);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public void confirmRegistration(@RequestParam String token) {
//        registrationService.confirmRegistration(token);
    }


}
