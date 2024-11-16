package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.BeneficiaryDTO;
import com.hexaware.MaverickBank.entity.Account;
import com.hexaware.MaverickBank.entity.Beneficiary;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.globalexception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.AccountRepository;
import com.hexaware.MaverickBank.repository.BeneficiaryRepository;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import com.hexaware.MaverickBank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BeneficiaryServiceImpl(BeneficiaryRepository beneficiaryRepository, AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BeneficiaryDTO createBeneficiary(BeneficiaryDTO beneficiaryDTO) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryName(beneficiaryDTO.getName());
        beneficiary.setRelationship(beneficiaryDTO.getRelationship());
//        beneficiary.setAccountNumber(beneficiaryDTO.getAccountNumber());

        if (beneficiaryDTO.getLinkedCustomerId() != null) {
            Customer linkedCustomer = customerRepository.findById(beneficiaryDTO.getLinkedCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            beneficiary.setLinkedCustomer(linkedCustomer);
        }

        Set<Account> accounts = beneficiaryDTO.getAccountIds().stream()
                .map(accountId -> accountRepository.findById(accountId)
                        .orElseThrow(() -> new ResourceNotFoundException("Account not found")))
                .collect(Collectors.toSet());
        beneficiary.setAccounts(accounts);

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

        if (beneficiaryDTO.getLinkedCustomerId() != null) {
            Customer linkedCustomer = customerRepository.findById(beneficiaryDTO.getLinkedCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            beneficiary.setLinkedCustomer(linkedCustomer);
        } else {
            beneficiary.setLinkedCustomer(null);
        }

        beneficiary.setBeneficiaryName(beneficiaryDTO.getName());
        beneficiary.setRelationship(beneficiaryDTO.getRelationship());


        if (beneficiaryDTO.getAccountIds() != null) {
            Set<Account> updatedAccounts = beneficiaryDTO.getAccountIds().stream()
                    .map(accountId -> accountRepository.findById(accountId)
                            .orElseThrow(() -> new ResourceNotFoundException("Account not found")))
                    .collect(Collectors.toSet());
            beneficiary.setAccounts(updatedAccounts);
        }

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
        dto.setLinkedCustomerId(beneficiary.getLinkedCustomer() != null ? beneficiary.getLinkedCustomer().getCustomerId() : null);
        dto.setAccountIds(beneficiary.getAccounts().stream().map(Account::getAccountId).collect(Collectors.toSet()));
        return dto;
    }
}