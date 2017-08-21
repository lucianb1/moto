package ro.motorzz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.model.registration.RegistrationRequest;
import ro.motorzz.service.api.RegistrationService;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void register(RegistrationRequest request) {

    }
}
