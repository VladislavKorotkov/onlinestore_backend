package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.services.BrandService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BrandValidator implements Validator {

    private BrandService brandService;

    public BrandValidator(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Brand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Brand brand = (Brand) target;
        try{
            brandService.loadBrandByName(brand.getName());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("name", "", "Данный бренд уже добавлен");
    }
}
