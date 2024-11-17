package com.hexaware.MaverickBank.controller;

import com.hexaware.MaverickBank.dto.BeneficiaryDTO;
import com.hexaware.MaverickBank.service.BeneficiaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficiaryController.class)
public class BeneficiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficiaryService beneficiaryService;

    @Test
    void testGetAllBeneficiaries() throws Exception {
        BeneficiaryDTO beneficiary = new BeneficiaryDTO();
        beneficiary.setBeneficiaryId(1);
        beneficiary.setName("Ansh");
        beneficiary.setRelationship("Brother");

        when(beneficiaryService.getAllBeneficiaries()).thenReturn(Collections.singletonList(beneficiary));

        mockMvc.perform(get("/api/beneficiaries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].beneficiaryId").value(1))
                .andExpect(jsonPath("$[0].name").value("Ansh"))
                .andExpect(jsonPath("$[0].relationship").value("Brother"));
    }
}