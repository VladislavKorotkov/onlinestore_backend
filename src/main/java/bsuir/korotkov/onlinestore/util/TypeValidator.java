package bsuir.korotkov.onlinestore.util;

import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.services.TypeService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TypeValidator implements Validator {
    private TypeService typeService;

    public TypeValidator(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Type.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Type type = (Type) target;
        try{
            typeService.loadTypeByName(type.getName());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("name", "", "Данный тип уже добавлен");

    }
}
