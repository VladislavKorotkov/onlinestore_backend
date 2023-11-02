package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotNull;

public class OrderDTO {

    @NotNull(message = "Адрес не может быть пустым")
    private int address_id;

    @NotNull(message = "Оплата не может быть пустой")
    private boolean is_paid;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public OrderDTO(int address_id, boolean is_paid) {
        this.address_id = address_id;
        this.is_paid = is_paid;
    }

    public OrderDTO() {
    }
}
