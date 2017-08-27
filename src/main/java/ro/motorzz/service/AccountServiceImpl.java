package ro.motorzz.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.account.AccountType;
import ro.motorzz.model.registration.RegistrationRequest;
import ro.motorzz.repository.AccountRepository;
import ro.motorzz.service.api.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account registerAccount(RegistrationRequest request) {
        return accountRepository.saveAccount(request.getEmail(), request.getPassword(), AccountType.USER, AccountStatus.PENDING);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
