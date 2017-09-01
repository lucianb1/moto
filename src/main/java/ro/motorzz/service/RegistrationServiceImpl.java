package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.ConflictException;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.core.exception.PreconditionFailedException;
import ro.motorzz.core.utils.TokenUtils;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.registration.RegistrationRequest;
import ro.motorzz.model.token.registration.RegistrationToken;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.RegistrationTokenRepository;
import ro.motorzz.service.api.AccountService;
import ro.motorzz.service.api.AuthenticationService;
import ro.motorzz.service.api.MailService;
import ro.motorzz.service.api.RegistrationService;

import java.time.LocalDateTime;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationTokenRepository registrationTokenRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final MailService mailService;
    private final AuthenticationService authenticationService;
    private final int durationInHours;

    public RegistrationServiceImpl(
            RegistrationTokenRepository registrationTokenRepository,
            AccountRepository accountRepository,
            AccountService accountService,
            MailService mailService,
            AuthenticationService authenticationService,
            @Value("${properties.registrationTokenDurationInHours}") int durationInHours
    ) {
        this.registrationTokenRepository = registrationTokenRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.mailService = mailService;
        this.authenticationService = authenticationService;
        this.durationInHours = durationInHours;
    }

    @Override
    public void register(RegistrationRequest request) {
        try {
            Account foundAccount = accountRepository.findAccountByEmail(request.getEmail());
            if (foundAccount.getStatus().equals(AccountStatus.PENDING)) {
                resendConfirmationEmail(foundAccount);
                throw new ConflictException("New confirmation email has been resend");
            }else{
                throw new PreconditionFailedException("Account status is not PENDING");
            }
        } catch (NotFoundException e) { //email is unique
            Account createdAccount = accountService.registerAccount(request);
            RegistrationToken registrationToken = registrationTokenRepository.saveRegistrationToken(this.generateUniqueToken(), LocalDateTime.now().plusHours(durationInHours), createdAccount.getId());
            mailService.sendRegisterConfirmationEmail(createdAccount.getEmail(), registrationToken.getToken());
        }
    }

    @Override
    public LoginResponseJson confirmRegistration(String token) {
        return authenticationService.confirmRegistration(token);
    }

    private String generateUniqueToken() {
        String token = TokenUtils.generateToken();
        while (!registrationTokenRepository.isTokenUnique(token)) {
            token = TokenUtils.generateToken();
        }
        return token;
    }

    private void resendConfirmationEmail(Account account) {
        RegistrationToken registrationToken = registrationTokenRepository.findByAccountId(account.getId());
        mailService.sendRegisterConfirmationEmail(account.getEmail(), registrationToken.getToken());
    }
}
