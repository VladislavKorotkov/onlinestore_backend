package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AccountDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        brandRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void handlePostBrandWithoutAccess_ReturnsForbiddenStatus() throws Exception {
        Brand brand = new Brand("Apple", "USA");
        createAccount("user@mail.ru", "1234", "ROLE_ADMIN");
        AccountDTO accountDTO = new AccountDTO("user@mail.ru", "1234");
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login").content(objectMapper.writeValueAsString(accountDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        String jwtToken = objectMapper.readTree(json).get("jwtToken").asText();
        mockMvc.perform(
                        post("/api/brands")
                                .content(objectMapper.writeValueAsString(brand))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer "  + jwtToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void handlePostBrandWithAccess_ReturnsOkStatus() throws Exception {

        Brand brand = new Brand("Apple", "USA");
        mockMvc.perform(
                        post("/api/brands")
                                .content(objectMapper.writeValueAsString(brand))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    Account createAccount(String username, String password, String role){
        Account account = new Account(username, passwordEncoder.encode(password), role);
        return accountRepository.save(account);
    }


}