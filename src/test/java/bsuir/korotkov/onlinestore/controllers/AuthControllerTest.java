package bsuir.korotkov.onlinestore.controllers;

import bsuir.korotkov.onlinestore.dto.AccountDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.repositories.AccountRepository;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AuthControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        accountRepository.deleteAll();
    }

    @Test
    public void handlePostCreatingANewAccountWithAnExistingUsername_ReturnsConflictStatus() throws Exception {
        createAccount("vlad@mail.ru", "1234", "ROLE_ADMIN");
        AccountDTO accountDTO = new AccountDTO("vlad@mail.ru", "1234");
        mockMvc.perform(
                        post("/api/auth/registration")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void handlePostCreatingANewAccount_ReturnsOkStatus() throws Exception{
        AccountDTO accountDTO = new AccountDTO("vlad@mail.ru", "1234");
        mockMvc.perform(
                        post("/api/auth/registration")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void handlePostLoginAccount_ReturnsOkStatus() throws Exception{
        createAccount("vlad@mail.ru", "1234", "ROLE_ADMIN");
        AccountDTO accountDTO = new AccountDTO("vlad@mail.ru", "1234");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(accountDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    Account createAccount(String username, String password, String role){
        Account account = new Account(username, passwordEncoder.encode(password), role);
        return accountRepository.save(account);
    }

}