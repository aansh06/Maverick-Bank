package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.BeneficiaryDTO;
import com.hexaware.MaverickBank.entity.Beneficiary;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.BeneficiaryRepository;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import com.hexaware.MaverickBank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BeneficiaryServiceImpl(BeneficiaryRepository beneficiaryRepository, CustomerRepository customerRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BeneficiaryDTO createBeneficiary(BeneficiaryDTO beneficiaryDTO) {
        Customer customer = customerRepository.findById(beneficiaryDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryName(beneficiaryDTO.getName());
        beneficiary.setRelationship(beneficiaryDTO.getRelationship());
//        beneficiary.setAccountNumber(beneficiaryDTO.getAccountNumber());
        beneficiary.setCustomer(customer);

        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return convertToDTO(savedBeneficiary);
    }

    @Override
    public BeneficiaryDTO getBeneficiaryById(Integer beneficiaryId) {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found"));
        return convertToDTO(beneficiary);
    }

    @Override
    public List<BeneficiaryDTO> getAllBeneficiaries() {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        return beneficiaries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BeneficiaryDTO updateBeneficiary(Integer beneficiaryId, BeneficiaryDTO beneficiaryDTO) {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found"));

        Customer customer = customerRepository.findById(beneficiaryDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        beneficiary.setBeneficiaryName(beneficiaryDTO.getName());
        beneficiary.setRelationship(beneficiaryDTO.getRelationship());
//        beneficiary.setAccountNumber(beneficiaryDTO.getAccountNumber());
        beneficiary.setCustomer(customer);

        Beneficiary updatedBeneficiary = beneficiaryRepository.save(beneficiary);
        return convertToDTO(updatedBeneficiary);
    }

    @Override
    public void deleteBeneficiary(Integer beneficiaryId) {
        if (!beneficiaryRepository.existsById(beneficiaryId)) {
            throw new ResourceNotFoundException("Beneficiary not found");
        }
        beneficiaryRepository.deleteById(beneficiaryId);
    }

    private BeneficiaryDTO convertToDTO(Beneficiary beneficiary) {
        BeneficiaryDTO dto = new BeneficiaryDTO();
        dto.setBeneficiaryId(beneficiary.getBeneficiaryId());
        dto.setName(beneficiary.getBeneficiaryName());
        dto.setRelationship(beneficiary.getRelationship());
//        dto.setAccountNumber(beneficiary.getAccountNumber());
        dto.setCustomerId(beneficiary.getCustomer().getCustomerId());
        return dto;
    }
}