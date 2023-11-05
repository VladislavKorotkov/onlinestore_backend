package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.TypeDTO;
import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.services.TypeService;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import bsuir.korotkov.onlinestore.util.TypeValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/types")
@CrossOrigin
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
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeDTO typeDTO,
                                           BindingResult bindingResult) throws ObjectNotCreatedException {
        Type type = convertToType(typeDTO);
        typeValidator.validate(type, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        typeService.createType(type);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Type> getAll(){
        return typeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public Type getOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        return typeService.loadTypeById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        typeService.deleteType(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id,
                                             @RequestBody @Valid TypeDTO typeDTO,
                                             BindingResult bindingResult) throws ObjectNotCreatedException, ObjectNotFoundException {
        Type type = convertToType(typeDTO);
        typeValidator.validate(type, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        typeService.updateType(id, type);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse("Тип не найден");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private Type convertToType(TypeDTO typeDTO) {
        return this.modelMapper.map(typeDTO, Type.class);
    }
}
