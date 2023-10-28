package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    public Address findAddressByCountryAndCityAndStreetAndNumber(String country, String city, String street, String number);
}
