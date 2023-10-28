package bsuir.korotkov.onlinestore.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Converter {
    static public String convertErrorsToString(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        boolean flag = true;
        for(FieldError error: errors){
            if(!flag){
                errorMsg.append("; ");
            }
            errorMsg.append(error.getDefaultMessage());
            flag = false;
        }
        return errorMsg.toString();
    }
}
