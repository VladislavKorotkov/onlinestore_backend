package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class BrandControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @AfterEach
    public void resetDb() {
        brandRepository.deleteAll();
    }

    @Test
    public void handlePostBrandWithoutAccess_ReturnsForbiddenStatus() throws Exception {
        Brand brand = new Brand("Apple", "USA");
        mockMvc.perform(
                        post("/api/brands")
                                .content(objectMapper.writeValueAsString(brand))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}