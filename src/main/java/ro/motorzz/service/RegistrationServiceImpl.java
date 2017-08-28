package ro.motorzz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.AlreadyExistsException;
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

    public RegistrationServiceImpl(
            RegistrationTokenRepository registrationTokenRepository,
            AccountRepository accountRepository,
            AccountService accountService,
            MailService mailService,
            AuthenticationService authenticationService
    ) {
        this.registrationTokenRepository = registrationTokenRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.mailService = mailService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void register(RegistrationRequest request) {
        boolean isEmailUnique = accountRepository.isEmailUnique(request.getEmail());
        if(!isEmailUnique){
            throw new AlreadyExistsException("");
        }
        Account account = accountService.registerAccount(request);
        RegistrationToken registrationToken = registrationTokenRepository.saveRegistrationToken(this.generateUniqueToken(), LocalDateTime.now().plusHours(8), account.getId());
        mailService.sendRegisterConfirmationEmail(account.getEmail(),registrationToken.getToken());
    }

    @Override
    public LoginResponseJson confirmRegistration(String token) {
        return authenticationService.confirmRegistration(token);
    }

    @Override
    public void resendRegistrationToken(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        if(account.getStatus().equals(AccountStatus.ACTIVE)){
            throw new PreconditionFailedException("Account is already ACTIVE");
        }
        RegistrationToken registrationToken = registrationTokenRepository.findByAccountId(account.getId());
        mailService.sendRegisterConfirmationEmail(account.getEmail(),registrationToken.getToken());
    }

    private String generateUniqueToken(){
        String token = TokenUtils.generateToken();
        while(!registrationTokenRepository.isTokenUnique(token)){
            token = TokenUtils.generateToken();
        }
        return token;
    }
}
