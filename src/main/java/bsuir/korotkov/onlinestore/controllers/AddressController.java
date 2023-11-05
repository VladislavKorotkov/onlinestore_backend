package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AddressDTO;
import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.services.AddressService;
import bsuir.korotkov.onlinestore.util.ErrorResponse;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api/addresses")
@CrossOrigin
@RestController
public class AddressController {

    private AddressService addressService;

    private ModelMapper modelMapper;

    public AddressController(AddressService addressService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<Address> getAll(){
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public Address getOne(@PathVariable("id") int id) throws ObjectNotFoundException {
        return addressService.loadAddressById(id);
    }

    @PostMapping()
    public ResponseEntity<Address> create(@RequestBody @Valid AddressDTO addressDTO){
        Address address = convertToAddress(addressDTO);
        Address new_address = addressService.createAddress(address);
        return new ResponseEntity<>(new_address, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException e){
        ErrorResponse response = new ErrorResponse("Адрес не найден");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    private Address convertToAddress(AddressDTO addressDTO) {
        return this.modelMapper.map(addressDTO, Address.class);
    }

}
