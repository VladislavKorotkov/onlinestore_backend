package bsuir.korotkov.onlinestore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class BrandDTO {
    @Column(name = "name")
    @NotEmpty(message="Название не может быть пустым")
    private String name;

    @Column(name="country")
    @NotEmpty(message = "Страна не может быть пустой")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BrandDTO() {
    }

    public BrandDTO(String name, String country) {
        this.name = name;
        this.country = country;
    }
}
