package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.BankDTO;
import com.hexaware.MaverickBank.entity.Bank;

import java.util.List;

public interface BankService {
    BankDTO createBank(BankDTO bank);
    BankDTO updateBank(Integer bankId, BankDTO bank);
    BankDTO getBankById(Integer bankId);
    List<BankDTO> getAllBanks();
    void deleteBank(Integer bankId);
}
