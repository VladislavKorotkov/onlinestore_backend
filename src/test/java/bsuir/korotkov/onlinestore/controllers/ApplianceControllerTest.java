package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.ApplianceDTORequest;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import bsuir.korotkov.onlinestore.repositories.TypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ApplianceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplianceRepository applianceRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        applianceRepository.deleteAll();
        accountRepository.deleteAll();
        brandRepository.deleteAll();
        typeRepository.deleteAll();
    }

    @Test
    public void handleGetAllAppliances_ReturnsValidResponseEntity() throws Exception {
        Type type = createTestType("Холодильик");
        Brand brand = createTestBrand("LG", "China");
        Appliance appliance = createTestAppliance("DS123", 123, "50a62691-137c-4dd9-8405-8e8292fca5f1.jpg", "Отличный холодильник",12, brand, type);
        this.mockMvc.perform(get("/api/appliances"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "name": "DS123",
                                        "price": 123,
                                        "img": "50a62691-137c-4dd9-8405-8e8292fca5f1.jpg",
                                        "description": "Отличный холодильник",
                                        "count": 12,
                                        "rating": 5
                                    }
                                ]
                                """)
                );
    }

    private Appliance createTestAppliance(String name, int price, String img, String description, int count, Brand brand, Type type) {
        Appliance appliance = new Appliance(name, price, img, description, count);
        appliance.setTypeApl(type);
        appliance.setBrandApl(brand);
        appliance.setRating(5);
        return applianceRepository.save(appliance);
    }

    private Type createTestType(String name) {
        Type type = new Type(name);
        return typeRepository.save(type);
    }

    private Brand createTestBrand(String name, String country) {
       Brand brand = new Brand(name, country);
        return brandRepository.save(brand);
    }

    Account createAccount(String username, String password, String role){
        Account account = new Account(username, passwordEncoder.encode(password), role);
        return accountRepository.save(account);
    }
}