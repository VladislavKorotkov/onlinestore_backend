package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AccountDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.security.JWTUtil;
import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import bsuir.korotkov.onlinestore.services.RegistrationService;
import bsuir.korotkov.onlinestore.util.AccountValidator;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin
@RestController
public class AuthController {
    private final RegistrationService registrationService;
    private final AccountValidator accountValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AccountDetailsService accountDetailsService;

    private final AuthenticationManager authenticationManager;

    public AuthController(RegistrationService registrationService, AccountValidator accountValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AccountDetailsService accountDetailsService, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.accountValidator = accountValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.accountDetailsService = accountDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid AccountDTO accountDTO,
                                                             BindingResult bindingResult) {
        Account account = convertToPerson(accountDTO);

        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(Map.of("message", bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.CONFLICT);
        }

        registrationService.register(account);

        String token = jwtUtil.generateToken(account);
        return new ResponseEntity<>(Map.of("jwtToken", token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AccountDTO accountDTO) {
        System.out.println(accountDTO.getUsername() + " " + accountDTO.getPassword());
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(accountDTO.getUsername(),
                        accountDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(Map.of("message", "Неверный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(accountDetailsService.loadAccountByUsername(accountDTO.getUsername()));
        return new ResponseEntity<>(Map.of("jwtToken", token), HttpStatus.OK);
    }

    public Account convertToPerson(AccountDTO accountDTO) {
        return this.modelMapper.map(accountDTO, Account.class);
    }
}
