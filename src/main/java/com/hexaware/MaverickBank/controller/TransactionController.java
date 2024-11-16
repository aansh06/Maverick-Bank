package com.hexaware.MaverickBank.controller;

import com.hexaware.MaverickBank.dto.TransactionDTO;
import com.hexaware.MaverickBank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable("id") Integer id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    //    to get all the transaction of an particular account by its account id
    @GetMapping("/account/{id}")
    public  ResponseEntity<List<TransactionDTO>> getAllTransactionsByAccount(@PathVariable("id") Integer id){
        List<TransactionDTO> transactions = transactionService.getTransactionsByAccountId(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    //    to get all the credit transaction of a particular account by its account id
    @GetMapping("/accountCredit/{id}")
    public  ResponseEntity<List<TransactionDTO>> getAllCreditTransactionsByAccount(@PathVariable("id") Integer id){
        List<TransactionDTO> transactions = transactionService.getCreditTransactions(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    //    to get all the debit transaction of a particular account by its account id
    @GetMapping("/accountDebit/{id}")
    public  ResponseEntity<List<TransactionDTO>> getAllDebitTransactionsByAccount(@PathVariable("id") Integer id){
        List<TransactionDTO> transactions = transactionService.getDebitTransactions(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}