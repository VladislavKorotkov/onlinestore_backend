package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AccountDTO;
import bsuir.korotkov.onlinestore.dto.AccountPutDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.security.JWTUtil;
import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import bsuir.korotkov.onlinestore.services.RegistrationService;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.AccountValidator;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectIsPresentException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

        registrationService.register(account, "ROLE_USER");

        String token = jwtUtil.generateToken(account);
        return new ResponseEntity<>(Map.of("jwtToken", token), HttpStatus.OK);
    }

    @PostMapping("/registrationForAdmin")
    public ResponseEntity<HttpStatus> performRegistrationForAdmin(@RequestBody @Valid AccountDTO accountDTO,
                                                                   BindingResult bindingResult) throws ObjectNotCreatedException {
        Account account = convertToPerson(accountDTO);

        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        if(accountDTO.getRole().isEmpty()){
            registrationService.register(account, "ROLE_USER");
        }
        else{
            registrationService.register(account, accountDTO.getRole());
        }
        jwtUtil.generateToken(account);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AccountDTO accountDTO) {
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

    @PutMapping("/change/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @RequestBody AccountPutDTO accountPutDTO) throws AccessException, ObjectNotFoundException, ObjectIsPresentException {
        Account account = parseUsername(token);
        System.out.println(token);
        String new_token = jwtUtil.generateToken(registrationService.update(accountPutDTO, id, account));
        return new ResponseEntity<>(Map.of("jwtToken", new_token), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAll(){
        return new ResponseEntity<>(accountDetailsService.loadAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id,@RequestHeader("Authorization") String token) throws AccessException, ObjectNotFoundException {
        Account account = parseUsername(token);
        registrationService.deleteAccount(id, account);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Account convertToPerson(AccountDTO accountDTO) {
        return this.modelMapper.map(accountDTO, Account.class);
    }
    private Account parseUsername(String token){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7));
        return accountDetailsService.loadAccountByUsername(username);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AccessException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectIsPresentException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
