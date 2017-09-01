package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.PreconditionFailedException;
import ro.motorzz.core.utils.TokenUtils;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.login.response.LoginResponseJson;
import ro.motorzz.model.resetpassword.ResetPasswordRequest;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.repository.ResetPasswordTokenRepository;
import ro.motorzz.service.api.AuthenticationService;
import ro.motorzz.service.api.MailService;
import ro.motorzz.service.api.ResetPasswordService;

import java.time.LocalDateTime;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final MailService mailService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final int durationInHours;
    private final AuthenticationService authenticationService;

    public ResetPasswordServiceImpl(
            ResetPasswordTokenRepository resetPasswordTokenRepository,
            MailService mailService,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            @Value("${properties.resetPasswordTokenDurationInHours}") int durationInHours,
            AuthenticationService authenticationService
    ) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.mailService = mailService;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.durationInHours = durationInHours;
        this.authenticationService = authenticationService;
    }

    @Override
    public void resetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        Account account = accountRepository.findAccountByEmail(resetPasswordRequest.getEmail());
        if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new PreconditionFailedException("Account status is not active");
        }
        String token = generateUniqueToken();
        resetPasswordTokenRepository.saveResetPasswordToken(token, LocalDateTime.now().plusHours(durationInHours), passwordEncoder.encode(resetPasswordRequest.getPassword()), account.getId());
        mailService.sendPasswordConfirmationEmail(account.getEmail(),token);
    }

    @Override
    public LoginResponseJson confirmResetPassword(String token) {
        return authenticationService.confirmResetPassword(token);
    }

    private String generateUniqueToken() {
        String token = TokenUtils.generateToken();
        while (!resetPasswordTokenRepository.isTokenUnique(token)) {
            token = TokenUtils.generateToken();
        }
        return token;
    }
}
