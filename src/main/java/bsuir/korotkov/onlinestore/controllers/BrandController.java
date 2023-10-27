package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.BrandDTO;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.services.BrandService;
import bsuir.korotkov.onlinestore.util.BrandValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/brand")
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
    public Map<String, String> createBrand(@RequestBody @Valid BrandDTO brandDTO,
                                           BindingResult bindingResult){
        Brand brand = convertToBrand(brandDTO);

        brandValidator.validate(brand, bindingResult);
        if(bindingResult.hasErrors()) {

            return Map.of("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        brandService.createBrand(brand);
        return Map.of("ok", "allOk");
    }

    @GetMapping
    public List<Brand> getAll() {
        return brandService.getAllBrands();
    }

    public Brand convertToBrand(BrandDTO brandDTO) {
        return this.modelMapper.map(brandDTO, Brand.class);
    }

}
