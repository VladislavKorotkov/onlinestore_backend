package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.ApplianceDTORequest;
import bsuir.korotkov.onlinestore.dto.ApplianceDTOResponse;
import bsuir.korotkov.onlinestore.services.ApplianceService;
import bsuir.korotkov.onlinestore.util.ApplianceDTOValidator;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/appliances")
@CrossOrigin
@RestController
public class ApplianceController {
    private final ApplianceDTOValidator applianceDTOValidator;

    private final ApplianceService applianceService;

    public ApplianceController(ApplianceDTOValidator applianceDTOValidator, ApplianceService applianceService) {
        this.applianceDTOValidator = applianceDTOValidator;
        this.applianceService = applianceService;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@Valid @ModelAttribute ApplianceDTORequest applianceDTO, BindingResult bindingResult) throws ObjectNotCreatedException, IOException, ObjectNotFoundException {
        System.out.println(applianceDTO.getDescription());
        applianceDTOValidator.validate(applianceDTO,bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        applianceService.createAppliance(applianceDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // charset=UTF-8
    @GetMapping(produces = "application/json")
    public List<ApplianceDTOResponse> getAll(){
        return applianceService.getAllAppliances();
    }

    @GetMapping("/filter-brand-type")
    public List<ApplianceDTOResponse> getAllFilterBrandAndType(@RequestParam("brandId") int brandId, @RequestParam("typeId") int typeId) throws ObjectNotFoundException {
        return applianceService.getAllAppliancesFilterBrandAndType(brandId, typeId);
    }

    @GetMapping("/filter-brand")
    public List<ApplianceDTOResponse> getAllFilterBrand(@RequestParam("brandId") int brandId) throws ObjectNotFoundException {
        return applianceService.getAllAppliancesFilterBrand(brandId);
    }

    @GetMapping("/filter-type")
    public List<ApplianceDTOResponse> getAllFilterType(@RequestParam("typeId") int typeId) throws ObjectNotFoundException {
        return applianceService.getAllAppliancesFilterType(typeId);
    }


    @GetMapping("/{id}")
    public ApplianceDTOResponse getOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        ApplianceDTOResponse applianceDTOResponse = applianceService.loadApplianceById(id);
        return applianceDTOResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) throws ObjectNotFoundException {
        applianceService.deleteAppliance(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
