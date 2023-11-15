package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.AccountPutDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Cart;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import bsuir.korotkov.onlinestore.repositories.CartRepository;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.ObjectIsPresentException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public void register(Account account, String role){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(role);
        accountRepository.save(account);
        Cart cart = new Cart();
        cart.setAccount_cart(account);
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteAccount(int id, Account account) throws ObjectNotFoundException, AccessException {
        Optional<Account> account_opt = accountRepository.findById(id);
        if(account_opt.isEmpty()){
            throw new ObjectNotFoundException("Аккаунт не найден");
        }
        if(account.getId()==id){
            throw new AccessException("Невозможно удалить собственный аккаунт");
        }
        accountRepository.delete(account_opt.get());
    }

    @Transactional
    public Account update(AccountPutDTO accountPutDTO, int id, Account account) throws ObjectNotFoundException, AccessException, ObjectIsPresentException {
        Optional<Account> account_opt = accountRepository.findByUsername(account.getUsername());
        if(account_opt.isEmpty()){
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if(account_opt.get().getId()!=id){
            throw new AccessException("Доступ запрещен");
        }
        if(!passwordEncoder.matches(accountPutDTO.getOldPassword(),account_opt.get().getPassword())){
            throw new AccessException("Неверный пароль");
        }
        Account account_rep = account_opt.get();
        if(!accountPutDTO.getUsername().equals(account_rep.getUsername())){
            Optional<Account> account_repeat = accountRepository.findByUsername(accountPutDTO.getUsername());
            if(account_repeat.isPresent()){
                throw new ObjectIsPresentException("Данный email уже используется");
            }
            account_rep.setUsername(accountPutDTO.getUsername());
        }
        if(!accountPutDTO.getNewPassword().isEmpty()){
            account_rep.setPassword(passwordEncoder.encode(accountPutDTO.getNewPassword()));
        }
        accountRepository.save(account_rep);
        return account_rep;
    }
}
