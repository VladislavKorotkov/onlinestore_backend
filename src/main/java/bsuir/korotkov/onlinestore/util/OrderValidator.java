package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.dto.AddressDTO;
import bsuir.korotkov.onlinestore.dto.OrderDTO;
import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
@Component
public class OrderValidator implements Validator {
    private final AddressRepository addressRepository;

    public OrderValidator(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDTO orderDTO = (OrderDTO) target;
        Optional<Address> address = addressRepository.findById(orderDTO.getAddress_id());
        if(address.isEmpty()){
            errors.rejectValue("address_id", "", "Адрес не найден");
        }
    }
}
