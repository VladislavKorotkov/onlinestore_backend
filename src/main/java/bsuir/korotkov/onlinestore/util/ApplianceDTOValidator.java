package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.dto.ApplianceDTORequest;
import bsuir.korotkov.onlinestore.services.BrandService;
import bsuir.korotkov.onlinestore.services.TypeService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ApplianceDTOValidator implements Validator {

    private final TypeService typeService;
    private final BrandService brandService;

    public ApplianceDTOValidator(TypeService typeService, BrandService brandService) {
        this.typeService = typeService;
        this.brandService = brandService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ApplianceDTORequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApplianceDTORequest applianceDTO = (ApplianceDTORequest) target;
        try {
            typeService.loadTypeById(applianceDTO.getType());
        } catch (ObjectNotFoundException e) {
            errors.rejectValue("type", "", "Данный тип отсутствует");
        }
        try {
            brandService.loadBrandById(applianceDTO.getBrand());
        } catch (ObjectNotFoundException e) {
            errors.rejectValue("brand", "", "Данный бренд отсутствует");
        }

    }
}
