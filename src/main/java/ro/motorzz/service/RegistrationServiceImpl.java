package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.AlreadyExistsException;
import ro.motorzz.core.utils.TokenUtils;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.registration.RegistrationRequest;
import ro.motorzz.model.token.RegistrationToken;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.RegistrationTokenRepository;
import ro.motorzz.service.api.AccountService;
import ro.motorzz.service.api.MailService;
import ro.motorzz.service.api.RegistrationService;

import java.time.LocalDateTime;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

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

    private String generateUniqueToken(){
        String token = TokenUtils.generateToken();
        while(!registrationTokenRepository.isTokenUnique(token)){
            token = TokenUtils.generateToken();
        }
        return token;
    }
}
