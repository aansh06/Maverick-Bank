package com.hexaware.MaverickBank.service.impl;


import com.hexaware.MaverickBank.dto.BankDTO;
import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.BankRepository;
import com.hexaware.MaverickBank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public BankDTO createBank(BankDTO bankDTO) {
        Bank bank = new Bank();
        bank.setBankName(bankDTO.getBankName());
        bank.setAddress(bankDTO.getAddress());
        bank.setContactNumber(bankDTO.getContactNumber());
        bank.setEmail(bankDTO.getEmail());
        bank.setCreatedAt(LocalDateTime.now());
        bank.setUpdatedAt(LocalDateTime.now());
        Bank savedBank = bankRepository.save(bank);
        return convertToDTO(savedBank);
    }



    public BankDTO updateBank(Integer bankId, BankDTO bankDTO) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));

        bank.setBankName(bankDTO.getBankName());
        bank.setAddress(bankDTO.getAddress());
        bank.setContactNumber(bankDTO.getContactNumber());
        bank.setEmail(bankDTO.getEmail());
        bank.setUpdatedAt(LocalDateTime.now());

        Bank updatedBank = bankRepository.save(bank);
        return convertToDTO(updatedBank);
    }


    @Override
    public BankDTO getBankById(Integer bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        return convertToDTO(bank);
    }

    @Override
    public List<BankDTO> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    @Override
    public void deleteBank(Integer bankId) {
        if (!bankRepository.existsById(bankId)) {
            throw new RuntimeException("Bank not found");
        }
        bankRepository.deleteById(bankId);
    }

    private BankDTO convertToDTO(Bank bank) {
        BankDTO dto = new BankDTO();
        dto.setBankId(bank.getBankId());
        dto.setBankName(bank.getBankName());
        dto.setAddress(bank.getAddress());
        dto.setContactNumber(bank.getContactNumber());
        dto.setEmail(bank.getEmail());
        return dto;
    }
}