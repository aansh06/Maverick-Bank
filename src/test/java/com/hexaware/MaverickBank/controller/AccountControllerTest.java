package com.hexaware.MaverickBank.controller;

import com.hexaware.MaverickBank.dto.AccountDTO;
import com.hexaware.MaverickBank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void testGetAllAccounts() throws Exception {
        AccountDTO account1 = new AccountDTO();
        account1.setAccId(1);
        account1.setAccountNumber("1234567890");
        account1.setBalance(5000.0);

        AccountDTO account2 = new AccountDTO();
        account2.setAccId(2);
        account2.setAccountNumber("0987654321");
        account2.setBalance(10000.0);

        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(account1, account2));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accId").value(1))
                .andExpect(jsonPath("$[0].balance").value(5000.0))
                .andExpect(jsonPath("$[1].accId").value(2))
                .andExpect(jsonPath("$[1].balance").value(10000.0));
    }
}