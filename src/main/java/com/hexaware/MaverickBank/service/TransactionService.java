package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransactionById(Integer transactionId);
    List<TransactionDTO> getAllTransactions();
    List<TransactionDTO> getTransactionsByAccountId(Integer accountId);
    List<TransactionDTO> getCreditTransactions(Integer accountId);
    List<TransactionDTO> getDebitTransactions(Integer accountId);

}
