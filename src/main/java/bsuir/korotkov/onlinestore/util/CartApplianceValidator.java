package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.dto.CartDTORequest;
import bsuir.korotkov.onlinestore.dto.CartDTOResponse;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CartApplianceValidator implements Validator {

    private final ApplianceRepository applianceRepository;

    public CartApplianceValidator(ApplianceRepository applianceRepository) {
        this.applianceRepository = applianceRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CartDTORequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartDTORequest cartDTORequest = (CartDTORequest) target;
        Optional<Appliance> appliance = applianceRepository.findById(cartDTORequest.getAppliance_id());
        if(appliance.isEmpty()){
            errors.rejectValue("appliance_id", "", "Товар не найден");
        }
        else if (appliance.get().getCount() < cartDTORequest.getCount()){
            errors.rejectValue("count", "", "Данное количество товара отсутствует");
        }
    }
}
