package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("ROLE_USER");
        accountRepository.save(account);
    }

}
