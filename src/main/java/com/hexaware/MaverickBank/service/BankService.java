package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.entity.Bank;

import java.util.List;

public interface BankService {
    Bank createBank(Bank bank);
    Bank updateBank(Integer bankId, Bank bank);
    Bank getBankById(Integer bankId);
    List<Bank> getAllBanks();
    void deleteBank(Integer bankId);
}
