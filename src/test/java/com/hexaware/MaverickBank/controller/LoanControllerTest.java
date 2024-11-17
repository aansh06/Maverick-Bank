package com.hexaware.MaverickBank.controller;


import com.hexaware.MaverickBank.dto.LoanDTO;
import com.hexaware.MaverickBank.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Test
    void testGetLoansByCustomerId() throws Exception {
        LoanDTO loan = new LoanDTO();
        loan.setLoanId(1);
        loan.setPrincipalAmount(500000.0);
        loan.setCustomerId(1);

        when(loanService.getLoansByCustomerId(1)).thenReturn(Collections.singletonList(loan));

        mockMvc.perform(get("/api/loans/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].loanId").value(1))
                .andExpect(jsonPath("$[0].principalAmount").value(500000));
    }
}