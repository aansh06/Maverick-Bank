package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.TransactionDTO;
import com.hexaware.MaverickBank.entity.Account;
import com.hexaware.MaverickBank.entity.Transaction;
import com.hexaware.MaverickBank.globalexception.InsufficientFundsException;
import com.hexaware.MaverickBank.globalexception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.AccountRepository;
import com.hexaware.MaverickBank.repository.TransactionRepository;
import com.hexaware.MaverickBank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Account sourceAccount = accountRepository.findById(transactionDTO.getSourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

        Account destinationAccount=null;
        if(transactionDTO.getDestinationAccountId() != null) {
            destinationAccount = accountRepository.findById(transactionDTO.getDestinationAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination account not found"));
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate() != null ? transactionDTO.getTransactionDate() : LocalDateTime.now());
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);

        double transactionAmount = transactionDTO.getAmount();


        switch (transactionDTO.getTransactionType().toLowerCase()) {
            case "deposit":

                if (sourceAccount.getBalance().compareTo(transactionAmount) < 0) {
                    throw new InsufficientFundsException("Insufficient balance in source account");
                }
                sourceAccount.setBalance(sourceAccount.getBalance() + transactionAmount);
                break;

            case "withdraw":

                sourceAccount.setBalance(sourceAccount.getBalance() - transactionAmount);
                break;

            case "transfer":

                if (destinationAccount == null) {
                    throw new IllegalArgumentException("Destination account is required for transfer transactions");
                }
                if (sourceAccount.getBalance().compareTo(transactionAmount) < 0) {
                    throw new InsufficientFundsException("Insufficient balance in source account for transfer");
                }
                sourceAccount.setBalance(sourceAccount.getBalance() - transactionAmount);
                destinationAccount.setBalance(destinationAccount.getBalance() + transactionAmount);
                break;

            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }

        accountRepository.save(sourceAccount);
        if(destinationAccount!=null) {
            accountRepository.save(destinationAccount);
        }
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);

    }

    @Override
    public TransactionDTO getTransactionById(Integer transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return convertToDTO(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Integer accountId) {
        List<Transaction> accountTransactions = transactionRepository.findBySourceAccount_AccountIdOrDestinationAccount_AccountId(accountId, accountId);
        return accountTransactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setSourceAccountId(transaction.getSourceAccount().getAccountId());
        if (transaction.getDestinationAccount() != null) {
            dto.setDestinationAccountId(transaction.getDestinationAccount().getAccountId());
        }
        return dto;
    }

    @Override
    public List<TransactionDTO> getCreditTransactions(Integer accountId) {
        List<Transaction> creditTransactions = transactionRepository
                .findByTransactionTypeAndSourceAccount_AccountIdOrTransactionTypeAndDestinationAccount_AccountId(
                        "deposit", accountId, "transfer", accountId);
        return creditTransactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getDebitTransactions(Integer accountId) {
        List<Transaction> debitTransactions = transactionRepository
                .findByTransactionTypeAndSourceAccount_AccountIdOrTransactionTypeAndSourceAccount_AccountId(
                        "withdraw", accountId, "transfer", accountId);
        return debitTransactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
