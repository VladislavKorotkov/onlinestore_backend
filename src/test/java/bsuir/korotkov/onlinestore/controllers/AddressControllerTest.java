package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AddressControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddressRepository addressRepository;

    @AfterEach
    public void resetDb() {
        addressRepository.deleteAll();
    }

    @Test
    public void handleGetAllAddresses_ReturnsValidResponseEntity() throws Exception {
        Address address1 = createTestAddress("Беларусь", "Минск", "Якуба Коласа", "28");
        Address address2 = createTestAddress("Беларусь","Браслав", "Советская", "12");
        this.mockMvc.perform(get("/api/addresses"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "country": "Беларусь",
                                        "city": "Минск",
                                        "street": "Якуба Коласа",
                                        "number": "28"
                                    },
                                    {
                                        "country": "Беларусь",
                                        "city": "Браслав",
                                        "street": "Советская",
                                        "number": "12"
                                    }
                                ]
                                """)
                );
    }

    @Test
    public void handleGetAddressByWrongId_ReturnsNotFoundStatus() throws Exception {
        Address address1 = createTestAddress("Беларусь", "Минск", "Якуба Коласа", "28");
        Address address2 = createTestAddress("Беларусь","Браслав", "Советская", "12");
        String path = "/api/addresses/133";
        this.mockMvc.perform(get(path))
                // then
                .andExpectAll(
                        status().isNotFound()
                );
    }


    @Test
    public void handleGetAddressById_ReturnsValidResponseEntity() throws Exception {
        Address address1 = createTestAddress("Беларусь", "Минск", "Якуба Коласа", "28");
        Address address2 = createTestAddress("Беларусь","Браслав", "Советская", "12");
        String path = "/api/addresses/" + address1.getId();
        this.mockMvc.perform(get(path))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "country": "Беларусь",
                                        "city": "Минск",
                                        "street": "Якуба Коласа",
                                        "number": "28"
                                    }
                                """)
                );
    }

    private Address createTestAddress(String country, String city, String street, String number) {
        Address address = new Address(country, city, street, number);
        return addressRepository.save(address);
    }


}