package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {
    private final AccountDetailsService accountDetailsService;

    public AccountValidator(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        try{
            accountDetailsService.loadUserByUsername(account.getUsername());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("username", "", "Человек с таким email уже существует");
    }
}
