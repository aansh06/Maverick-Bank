package com.hexaware.MaverickBank.controller;


import com.hexaware.MaverickBank.dto.TransactionDTO;
import com.hexaware.MaverickBank.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testGetAllTransactions() throws Exception {
        TransactionDTO transaction1 = new TransactionDTO();
//        transaction1.setTransactionId(1);
        transaction1.setTransactionType("deposit");
        transaction1.setAmount(5000.0);
        transaction1.setTransactionDate(LocalDateTime.now());

        TransactionDTO transaction2 = new TransactionDTO();
//        transaction2.setTransactionId(2);
        transaction2.setTransactionType("withdraw");
        transaction2.setAmount(2000.0);
        transaction2.setTransactionDate(LocalDateTime.now());

        when(transactionService.getAllTransactions()).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].transactionType").value("deposit"))
//                .andExpect(jsonPath("$[1].transactionId").value(2))
                .andExpect(jsonPath("$[1].transactionType").value("withdraw"));
    }
}