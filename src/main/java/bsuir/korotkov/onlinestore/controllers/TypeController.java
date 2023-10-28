package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.TypeDTO;
import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.services.TypeService;
import bsuir.korotkov.onlinestore.util.TypeValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/type")
@RestController
public class TypeController {
    private ModelMapper modelMapper;
    private TypeService typeService;
    private TypeValidator typeValidator;

    public TypeController(ModelMapper modelMapper, TypeService typeService, TypeValidator typeValidator) {
        this.modelMapper = modelMapper;
        this.typeService = typeService;
        this.typeValidator = typeValidator;
    }

    @PostMapping
    public Map<String, String> create(@RequestBody @Valid TypeDTO typeDTO,
                                           BindingResult bindingResult){
        Type type = convertToType(typeDTO);

        typeValidator.validate(type, bindingResult);
        if(bindingResult.hasErrors()) {

            return Map.of("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        typeService.createType(type);
        return Map.of("ok", "allOk");
    }

    @GetMapping
    public List<Type> getAll(){
        return typeService.getAllTypes();
    }


    public Type convertToType(TypeDTO typeDTO) {
        return this.modelMapper.map(typeDTO, Type.class);
    }
}
