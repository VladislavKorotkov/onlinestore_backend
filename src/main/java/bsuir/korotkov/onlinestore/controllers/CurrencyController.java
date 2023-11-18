package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.CurrencyDTOResponse;
import bsuir.korotkov.onlinestore.services.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/currencies")
@CrossOrigin
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<CurrencyDTOResponse> get(){
        return new ResponseEntity<>(currencyService.getCurrencies(), HttpStatus.OK);
    }
}
