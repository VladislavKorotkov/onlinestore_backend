package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Address loadAddressById(int id) throws ObjectNotFoundException {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional
    public Address createAddress(Address address){

        Optional<Address> old_address = Optional.ofNullable(addressRepository.findAddressByCountryAndCityAndStreetAndNumber(address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getNumber()));
        if(old_address.isPresent()){
            return old_address.get();
        }

        addressRepository.save(address);
        return address;
    }

}

