package bsuir.korotkov.onlinestore.util.currency;

import java.util.List;

public class Rates {
    private List<Currency> rates;

    public List<Currency> getRates() {
        return rates;
    }

    public void setRates(List<Currency> rates) {
        this.rates = rates;
    }

    public Rates() {
    }

    public Rates(List<Currency> rates) {
        this.rates = rates;
    }
}
