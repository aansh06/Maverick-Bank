package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.AccountDTO;
import com.hexaware.MaverickBank.entity.Account;
import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.globalexception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.AccountRepository;
import com.hexaware.MaverickBank.repository.BankRepository;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import com.hexaware.MaverickBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BankRepository bankRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Bank bank = bankRepository.findById(accountDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));

        Account account = new Account();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setBalance(accountDTO.getBalance() != null ? accountDTO.getBalance() : 0);
        account.setAccountType(accountDTO.getAccountType());
        account.setStatus(accountDTO.getStatus());
        account.setCustomer(customer);
        account.setBank(bank);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    @Override
    public AccountDTO updateAccount(Integer accountId, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Bank bank = bankRepository.findById(accountDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setBalance(accountDTO.getBalance());
        account.setAccountType(accountDTO.getAccountType());
        account.setStatus(accountDTO.getStatus());
        account.setCustomer(customer);
        account.setBank(bank);
        account.setUpdatedAt(LocalDateTime.now());

        Account updatedAccount = accountRepository.save(account);
        return convertToDTO(updatedAccount);
    }

    @Override
    public AccountDTO getAccountById(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return convertToDTO(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Integer accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found");
        }
        accountRepository.deleteById(accountId);
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccId(account.getAccountId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setStatus(account.getStatus());
        dto.setCustomerId(account.getCustomer().getCustomerId());
        dto.setBankId(account.getBank().getBankId());
        return dto;
    }
}