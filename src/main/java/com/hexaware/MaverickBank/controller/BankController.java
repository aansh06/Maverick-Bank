package com.hexaware.MaverickBank.controller;


import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createdBank = bankService.createBank(bank);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable("id") Integer id) {
        Bank bank = bankService.getBankById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable("id") Integer id, @RequestBody Bank bank) {
        Bank updatedBank = bankService.updateBank(id, bank);
        return new ResponseEntity<>(updatedBank, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable("id") Integer id) {
        bankService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
