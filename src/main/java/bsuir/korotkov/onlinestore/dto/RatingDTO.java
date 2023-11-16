package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class RatingDTO {
    @NotNull(message = "Рейтинг не может быть пустым")
    @Range(min = 1, max = 5)
    private int rate;

    @NotNull(message = "ID товара не может быть пустым")
    private int appliance_id;

    @NotNull(message = "ID заказа не может быть пустым")
    private int order_id;

    public int getAppliance_id() {
        return appliance_id;
    }

    public void setAppliance_id(int appliance_id) {
        this.appliance_id = appliance_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public RatingDTO(int rate, int appliance_id, int order_id) {
        this.rate = rate;
        this.appliance_id = appliance_id;
        this.order_id = order_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public RatingDTO() {
    }

    public RatingDTO(int rate) {
        this.rate = rate;
    }
}
