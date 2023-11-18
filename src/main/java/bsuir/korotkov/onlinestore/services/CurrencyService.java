package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.CurrencyDTOResponse;
import bsuir.korotkov.onlinestore.util.currency.Rates;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyService {
    private final WebClient webClient;

    public CurrencyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public CurrencyDTOResponse getCurrencies(){
        Rates rates = webClient
                .get()
                .retrieve()
                .bodyToMono(Rates.class)
                .block();
        CurrencyDTOResponse currencyDTOResponse = new CurrencyDTOResponse();
        double rub = rates.getRates().stream().filter(rate->rate.getIso().equals("RUB")).findFirst().get().getRate()/100;
        double usd = rates.getRates().stream().filter(rate->rate.getIso().equals("USD")).findFirst().get().getRate();
        currencyDTOResponse.setRub(rub);
        currencyDTOResponse.setUsd(usd);
        return currencyDTOResponse;
    }
}
