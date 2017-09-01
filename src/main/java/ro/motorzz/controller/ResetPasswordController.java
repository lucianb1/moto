package ro.motorzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.resetpassword.ResetPasswordJsonRequest;
import ro.motorzz.service.api.ResetPasswordService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    /**
     * 404 - email not found
     * 412 - account is not active
     */
    @RequestMapping(method = RequestMethod.POST)
    public void resetPassword(@Valid @RequestBody ResetPasswordJsonRequest resetPasswordJsonRequest) {
        resetPasswordService.resetPasswordRequest(resetPasswordJsonRequest);
    }

    /**
     * 412 - token expired
     * 404 - token not found
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public LoginResponseJson confirmResetPassword(@RequestParam String token) {
        return resetPasswordService.confirmResetPassword(token);
    }
}
