package bsuir.korotkov.onlinestore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class AddressDTO {
    @NotEmpty(message = "Страна не может быть пустой")
    private String country;

    @NotEmpty(message = "Город не может быть пустым")
    private String city;


    @NotEmpty(message = "Улица не может быть пустой")
    private String street;


    @NotEmpty(message = "Номер дома не может быть пустым")
    private String number_house;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber_house() {
        return number_house;
    }

    public void setNumber_house(String number_house) {
        this.number_house = number_house;
    }

    public AddressDTO() {
    }

    public AddressDTO(String country, String city, String street, String number_house) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number_house = number_house;
    }

}
