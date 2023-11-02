package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.BrandDTO;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.services.BrandService;
import bsuir.korotkov.onlinestore.util.BrandValidator;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
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
@RequestMapping("/api/brands")
@CrossOrigin
@RestController
public class BrandController {
    private BrandService brandService;
    private ModelMapper modelMapper;
    private BrandValidator brandValidator;

    public BrandController(BrandService brandService, ModelMapper modelMapper, BrandValidator brandValidator) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
        this.brandValidator = brandValidator;
    }
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BrandDTO brandDTO,
                                BindingResult bindingResult) throws ObjectNotCreatedException {
        Brand brand = convertToBrand(brandDTO);

        brandValidator.validate(brand, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        brandService.createBrand(brand);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Brand> getAll() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        return brandService.loadBrandById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id,
                                             @RequestBody @Valid BrandDTO BrandDTO,
                                             BindingResult bindingResult) throws ObjectNotCreatedException, ObjectNotFoundException {
        Brand brand = convertToBrand(BrandDTO);
        brandValidator.validate(brand, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        brandService.updateBrand(id, brand);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse("Бренд не найден");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Brand convertToBrand(BrandDTO brandDTO) {
        return this.modelMapper.map(brandDTO, Brand.class);
    }

}
