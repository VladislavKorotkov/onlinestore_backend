package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AccountDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.security.JWTUtil;
import bsuir.korotkov.onlinestore.services.RegistrationService;
import bsuir.korotkov.onlinestore.util.AccountValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final RegistrationService registrationService;
    private final AccountValidator accountValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    public AuthController(RegistrationService registrationService, AccountValidator accountValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.accountValidator = accountValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registration")
    public Map<String, String> wow(){
        return Map.of("asd", "sdf");
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid AccountDTO accountDTO,
                                                   BindingResult bindingResult) {
        Account account = convertToPerson(accountDTO);

        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка!");
        }

        registrationService.register(account);

        String token = jwtUtil.generateToken(account.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AccountDTO accountDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(accountDTO.getUsername(),
                        accountDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(accountDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    public Account convertToPerson(AccountDTO accountDTO) {
        return this.modelMapper.map(accountDTO, Account.class);
    }
}
