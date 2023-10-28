package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.ApplianceDTO;
import bsuir.korotkov.onlinestore.services.ApplianceService;
import bsuir.korotkov.onlinestore.util.ApplianceDTOValidator;
import bsuir.korotkov.onlinestore.util.Converter;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RequestMapping("/api/appliances")
@RestController
public class ApplianceController {
    private final ApplianceDTOValidator applianceDTOValidator;

    private final ApplianceService applianceService;

    public ApplianceController(ApplianceDTOValidator applianceDTOValidator, ApplianceService applianceService) {
        this.applianceDTOValidator = applianceDTOValidator;
        this.applianceService = applianceService;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@Valid @ModelAttribute ApplianceDTO applianceDTO, BindingResult bindingResult) throws ObjectNotCreatedException, IOException {
        applianceDTOValidator.validate(applianceDTO,bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ObjectNotCreatedException(Converter.convertErrorsToString(bindingResult));
        }
        applianceService.create(applianceDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
