package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.TransactionDTO;
import com.hexaware.MaverickBank.entity.Account;
import com.hexaware.MaverickBank.entity.Transaction;
import com.hexaware.MaverickBank.exception.InsufficientFundsException;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
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

        Account destinationAccount = null;
        if (transactionDTO.getDestinationAccountId() != null) {
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

        if ("Debit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
            if (sourceAccount.getBalance() < transactionAmount) {
                throw new InsufficientFundsException("Insufficient balance in source account");
            }
            sourceAccount.setBalance(sourceAccount.getBalance() - transactionAmount);
            destinationAccount.setBalance(destinationAccount.getBalance() + transactionAmount);
        }
//        } else if ("Credit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
//            sourceAccount.setBalance(sourceAccount.getBalance() + transactionAmount);
//        } else if ("Transfer".equalsIgnoreCase(transactionDTO.getTransactionType())) {
//            if (destinationAccount == null) {
//                throw new IllegalArgumentException("Destination account is required for transfer transactions");
//            }
//            if (sourceAccount.getBalance() < transactionAmount) {
//                throw new InsufficientFundsException("Insufficient balance in source account for transfer");
//            }
//            sourceAccount.setBalance(sourceAccount.getBalance() - transactionAmount);
//            destinationAccount.setBalance(destinationAccount.getBalance() + transactionAmount);
//        }

        accountRepository.save(sourceAccount);
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

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setSourceAccountId(transaction.getSourceAccount().getAccountId());
        dto.setDestinationAccountId(transaction.getDestinationAccount().getAccountId());

        return dto;
    }
}
