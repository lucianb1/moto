package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.AuthenticationException;
import ro.motorzz.core.exception.PreconditionFailedException;
import ro.motorzz.core.utils.TokenUtils;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.login.LoginRequest;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.token.registration.RegistrationToken;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.AuthenticationTokenRepository;
import ro.motorzz.repository.RegistrationTokenRepository;
import ro.motorzz.service.api.AuthenticationService;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final int durationInHours;
    private final RegistrationTokenRepository registrationTokenRepository;

    public AuthenticationServiceImpl(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationTokenRepository authenticationTokenRepository,
            @Value("${properties.authenticationTokenDurationInHours}") int durationInHours,
            RegistrationTokenRepository registrationTokenRepository)
    {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationTokenRepository = authenticationTokenRepository;
        this.durationInHours = durationInHours;
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Override
    public LoginResponseJson login(LoginRequest loginRequest) {
        Account account = accountRepository.findAccountByEmail(loginRequest.getEmail());

        if (account.getStatus().equals(AccountStatus.PENDING)) {
            throw new PreconditionFailedException("Account is not confirmed");
        } else if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            String token = generateUniqueToken();
            authenticationTokenRepository.saveAuthenticationToken(token, LocalDateTime.now().plusHours(durationInHours), account.getId());
            return new LoginResponseJson(token, account.getEmail());
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    @Override
    public LoginResponseJson confirmRegistration(String token) {
        RegistrationToken registrationToken = registrationTokenRepository.findByToken(token);
        Account account = accountRepository.findAccount(registrationToken.getAccountID());
        accountRepository.updateStatus(account.getId(), AccountStatus.ACTIVE);
        String authenticationToken = generateUniqueToken();
        authenticationTokenRepository.saveAuthenticationToken(authenticationToken, LocalDateTime.now().plusHours(durationInHours), account.getId());
        registrationTokenRepository.deleteRegistrationToken(registrationToken.getToken());
        return new LoginResponseJson(authenticationToken, account.getEmail());
    }

    @Override
    public void logout(String token) {
        authenticationTokenRepository.deleteAuthenticationToken(token);
    }

    private String generateUniqueToken() {
        String token = TokenUtils.generateToken();
        while (!authenticationTokenRepository.isTokenUnique(token)) {
            token = TokenUtils.generateToken();
        }
        return token;
    }
}
