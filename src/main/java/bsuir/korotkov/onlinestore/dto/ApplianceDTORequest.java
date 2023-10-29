package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ApplianceDTORequest {
    @NotEmpty(message = "Название товара не может быть пустым")
    private String name;

    @NotNull(message = "Цена не может быть пустой")
    private int price;

    @NotNull(message = "Цена не может быть пустой")
    private MultipartFile img;

    @NotEmpty(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Цена не может быть пустой")
    private int brand;

    @NotNull(message = "Цена не может быть пустой")
    private int type;

    @NotNull(message = "Количество не может быть пустым")
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ApplianceDTORequest() {
    }

    public ApplianceDTORequest(String name, int price, MultipartFile img, String description, int brand, int type, int count) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.brand = brand;
        this.type = type;
        this.count = count;
    }
}
