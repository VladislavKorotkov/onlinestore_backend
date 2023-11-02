package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotNull;

public class CartDTORequest {
    @NotNull(message = "id товара не может быть пустым")
    private int appliance_id;
    @NotNull(message = "Количество не может быть пустым")
    private int count;

    public CartDTORequest() {
    }

    public CartDTORequest(int appliance_id, int count) {
        this.appliance_id = appliance_id;
        this.count = count;
    }

    public int getAppliance_id() {
        return appliance_id;
    }

    public void setAppliance_id(int appliance_id) {
        this.appliance_id = appliance_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
