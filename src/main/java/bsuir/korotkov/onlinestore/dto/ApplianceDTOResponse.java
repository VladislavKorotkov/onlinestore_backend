package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ApplianceDTOResponse {

    private String name;
    private int price;
    private String img;
    private String description;
    private int count;
    private int brand;
    private String brand_name;
    private String type_name;
    private int type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ApplianceDTOResponse() {
    }

    public ApplianceDTOResponse(String name, int price, String img, String description, int count, int brand, String brand_name, String type_name, int type) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.count = count;
        this.brand = brand;
        this.brand_name = brand_name;
        this.type_name = type_name;
        this.type = type;
    }


}
