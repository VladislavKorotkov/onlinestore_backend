package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.CartDTORequest;
import bsuir.korotkov.onlinestore.dto.CartDTOResponse;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.security.JWTUtil;
import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import bsuir.korotkov.onlinestore.services.CartService;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.CartApplianceValidator;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectIsPresentException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/cart")
@CrossOrigin
@RestController
public class CartController {
    private final JWTUtil jwtUtil;
    private final AccountDetailsService accountDetailsService;
    private final CartApplianceValidator cartApplianceValidator;
    private final CartService cartService;

    public CartController(JWTUtil jwtUtil, AccountDetailsService accountDetailsService, CartApplianceValidator cartApplianceValidator, CartService cartService) {
        this.jwtUtil = jwtUtil;
        this.accountDetailsService = accountDetailsService;
        this.cartApplianceValidator = cartApplianceValidator;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addAppliance(@RequestBody @Valid CartDTORequest cartDTORequest,
                                                   @RequestHeader("Authorization") String token,
                                                   BindingResult bindingResult) throws ObjectNotCreatedException, ObjectNotFoundException, ObjectIsPresentException {
       Account account = parseUsername(token);
        cartApplianceValidator.validate(cartDTORequest, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        cartService.addAppliance(account, cartDTORequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<CartDTOResponse> getCartAppliances(@RequestHeader("Authorization") String token){
        Account account = parseUsername(token);
        return cartService.getAllAppliances(account);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<HttpStatus> deleteCartAppliance(@PathVariable("id") int id, @RequestHeader("Authorization") String token) throws AccessException, ObjectNotFoundException {
        Account account = parseUsername(token);
        cartService.deleteAppliance(account, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCartAppliances(@PathVariable("id") int id, @RequestBody Map<String, Integer> count, @RequestHeader("Authorization") String token, BindingResult bindingResult) throws AccessException, ObjectNotFoundException, ObjectNotCreatedException {
        Account account = parseUsername(token);
        cartService.updateAppliance(account,id,count.get("count"));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectIsPresentException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AccessException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Account parseUsername(String token){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7));
        return accountDetailsService.loadAccountByUsername(username);
    }
}
