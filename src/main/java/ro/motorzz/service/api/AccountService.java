package ro.motorzz.service.api;

import ro.motorzz.model.account.Account;
import ro.motorzz.model.registration.RegistrationRequest;

public interface AccountService {

    Account registerAccount(RegistrationRequest request);
}
