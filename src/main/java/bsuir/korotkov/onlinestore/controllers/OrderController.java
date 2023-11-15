package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.OrderDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Order;
import bsuir.korotkov.onlinestore.security.JWTUtil;
import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import bsuir.korotkov.onlinestore.services.OrderService;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectIsPresentException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import bsuir.korotkov.onlinestore.util.OrderValidator;
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
@RequestMapping("/api/orders")
@CrossOrigin
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderValidator orderValidator;
    private final JWTUtil jwtUtil;
    private final AccountDetailsService accountDetailsService;

    public OrderController(OrderService orderService, OrderValidator orderValidator, JWTUtil jwtUtil, AccountDetailsService accountDetailsService) {
        this.orderService = orderService;
        this.orderValidator = orderValidator;
        this.jwtUtil = jwtUtil;
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Integer>> addOrder(@RequestHeader("Authorization") String token,
                                          @RequestBody @Valid OrderDTO orderDTO,
                                          BindingResult bindingResult) throws ObjectNotCreatedException, ObjectNotFoundException {
        Account account = parseUsername(token);
        orderValidator.validate(orderDTO, bindingResult);
        if(bindingResult.hasErrors()){
            throw new ObjectNotFoundException(Converter.convertErrorsToString(bindingResult));
        }
        Order order = orderService.addOrder(orderDTO, account);
        return new ResponseEntity<>(Map.of("id", order.getId()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(@RequestHeader("Authorization") String token) throws ObjectNotFoundException {
        Account account = parseUsername(token);
        return new ResponseEntity<>(orderService.getAllOrdersForUser(account), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws AccessException, ObjectNotFoundException {
        Account account = parseUsername(token);
        return new ResponseEntity<>(orderService.getOneOrderById(account,id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllForAdmin(){
        return new ResponseEntity<>(orderService.getAllOrdersForAdmin(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id) throws ObjectNotFoundException, ObjectNotCreatedException {
        orderService.updateOrderToDelivered(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) throws ObjectNotFoundException {
        orderService.deleteOrder(id);
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
