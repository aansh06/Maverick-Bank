package com.hexaware.MaverickBank.service.impl;


import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.repository.BankRepository;
import com.hexaware.MaverickBank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank createBank(Bank bank) {
        bank.setCreatedAt(LocalDateTime.now());
        bank.setUpdatedAt(LocalDateTime.now());
        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(Integer bankId, Bank bankDetails) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        bank.setBankName(bankDetails.getBankName());
        bank.setAddress(bankDetails.getAddress());
        bank.setContactNumber(bankDetails.getContactNumber());
        bank.setEmail(bankDetails.getEmail());
        bank.setUpdatedAt(LocalDateTime.now());
        return bankRepository.save(bank);
    }

    @Override
    public Bank getBankById(Integer bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public void deleteBank(Integer bankId) {
        if (!bankRepository.existsById(bankId)) {
            throw new RuntimeException("Bank not found");
        }
        bankRepository.deleteById(bankId);
    }
}