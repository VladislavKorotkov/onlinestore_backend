package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Cart;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import bsuir.korotkov.onlinestore.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(AccountRepository accountRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("ROLE_USER");
        accountRepository.save(account);
        Cart cart = new Cart();
        cart.setAccount_cart(account);
        cartRepository.save(cart);
    }

}
