package com.hexaware.MaverickBank.controller;


import com.hexaware.MaverickBank.dto.LoanDTO;
import com.hexaware.MaverickBank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        LoanDTO createdLoan = loanService.createLoan(loanDTO);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable("id") Integer id) {
        LoanDTO loan = loanService.getLoanById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable("id") Integer id, @RequestBody LoanDTO loanDTO) {
        LoanDTO updatedLoan = loanService.updateLoan(id, loanDTO);
        return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable("id") Integer id) {
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}