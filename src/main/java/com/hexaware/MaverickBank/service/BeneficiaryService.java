package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.BeneficiaryDTO;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryDTO createBeneficiary(BeneficiaryDTO beneficiaryDTO);
    BeneficiaryDTO getBeneficiaryById(Integer beneficiaryId);
    List<BeneficiaryDTO> getAllBeneficiaries();
    BeneficiaryDTO updateBeneficiary(Integer beneficiaryId, BeneficiaryDTO beneficiaryDTO);
    void deleteBeneficiary(Integer beneficiaryId);
}
