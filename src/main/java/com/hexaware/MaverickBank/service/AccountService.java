package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Integer accountId, AccountDTO accountDTO);
    AccountDTO getAccountById(Integer accountId);
    List<AccountDTO> getAllAccounts();
    void deleteAccount(Integer accountId);
}
