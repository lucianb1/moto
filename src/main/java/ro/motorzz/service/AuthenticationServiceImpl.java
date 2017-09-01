package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import ro.motorzz.model.token.authentication.AuthenticationToken;
import ro.motorzz.model.token.registration.RegistrationToken;
import ro.motorzz.model.token.resetpassword.ResetPasswordToken;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.AuthenticationTokenRepository;
import ro.motorzz.repository.RegistrationTokenRepository;
import ro.motorzz.repository.ResetPasswordTokenRepository;
import ro.motorzz.security.PrincipalUser;
import ro.motorzz.service.api.AuthenticationService;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final int durationInHours;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public AuthenticationServiceImpl(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationTokenRepository authenticationTokenRepository,
            @Value("${properties.authenticationTokenDurationInHours}") int durationInHours,
            RegistrationTokenRepository registrationTokenRepository,
            ResetPasswordTokenRepository resetPasswordTokenRepository
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationTokenRepository = authenticationTokenRepository;
        this.durationInHours = durationInHours;
        this.registrationTokenRepository = registrationTokenRepository;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Override
    public LoginResponseJson login(LoginRequest loginRequest) {
        Account account = accountRepository.findAccountByEmail(loginRequest.getEmail());

        if (account.getStatus().equals(AccountStatus.PENDING)) {
            throw new PreconditionFailedException("Account is not confirmed");
        } else if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            return this.createAuthenticationToken(account);
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    @Override
    public LoginResponseJson confirmRegistration(String token) {
        RegistrationToken registrationToken = registrationTokenRepository.findByToken(token);
        Account account = accountRepository.findAccount(registrationToken.getAccountID());
        accountRepository.updateStatus(account.getId(), AccountStatus.ACTIVE);
        registrationTokenRepository.deleteRegistrationToken(registrationToken.getToken());
        return this.createAuthenticationToken(account);
    }

    @Override
    public void logout(String token) {
        authenticationTokenRepository.deleteAuthenticationToken(token);
    }

    @Override
    public PrincipalUser authenticateByToken(String token) {
        AuthenticationToken authenticationToken = authenticationTokenRepository.findByToken(token);
        Account account = accountRepository.findAccount(authenticationToken.getAccoutnID());
        return mapToPrincipal(account);
    }

    @Override
    public LoginResponseJson confirmResetPassword(String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken.getExpiresOn().compareTo(LocalDateTime.now()) < 0) {
            throw new PreconditionFailedException("Reset password token expired");
        }
        Account account = accountRepository.findAccount(resetPasswordToken.getAccountId());
        accountRepository.updatePassword(resetPasswordToken.getAccountId(), resetPasswordToken.getPassword());
        resetPasswordTokenRepository.deleteResetPasswordToken(resetPasswordToken.getToken());
        return this.createAuthenticationToken(account);
    }

    private String generateUniqueToken() {
        String token = TokenUtils.generateToken();
        while (!authenticationTokenRepository.isTokenUnique(token)) {
            token = TokenUtils.generateToken();
        }
        return token;
    }

    private PrincipalUser mapToPrincipal(Account account) {
        PrincipalUser principalUser = new PrincipalUser(account.getEmail(), "", Collections.singletonList(new SimpleGrantedAuthority(account.getType().name())));
        principalUser.setId(account.getId());
        principalUser.setAccountType(account.getType());
//        principalUser.setFirstLogin(account.getLoginTimes() == 0);
        return principalUser;
    }

    private LoginResponseJson createAuthenticationToken(Account account){
        String token = generateUniqueToken();
        authenticationTokenRepository.saveAuthenticationToken(token, LocalDateTime.now().plusHours(durationInHours), account.getId());
        return new LoginResponseJson(token, account.getEmail());
    }
}
