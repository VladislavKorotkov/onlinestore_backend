package bsuir.korotkov.onlinestore.dto;

public class CurrencyDTOResponse {
    private double usd;
    private double rub;

    public CurrencyDTOResponse() {
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getRub() {
        return rub;
    }

    public void setRub(double rub) {
        this.rub = rub;
    }

    public CurrencyDTOResponse(double usd, double rub) {
        this.usd = usd;
        this.rub = rub;
    }
}
