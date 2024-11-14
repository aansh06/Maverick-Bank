package com.hexaware.MaverickBank.controller;

import com.hexaware.MaverickBank.dto.BeneficiaryDTO;
import com.hexaware.MaverickBank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @Autowired
    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @PostMapping
    public ResponseEntity<BeneficiaryDTO> createBeneficiary(@RequestBody BeneficiaryDTO beneficiaryDTO) {
        BeneficiaryDTO createdBeneficiary = beneficiaryService.createBeneficiary(beneficiaryDTO);
        return new ResponseEntity<>(createdBeneficiary, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaryDTO> getBeneficiaryById(@PathVariable("id") Integer id) {
        BeneficiaryDTO beneficiary = beneficiaryService.getBeneficiaryById(id);
        return new ResponseEntity<>(beneficiary, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BeneficiaryDTO>> getAllBeneficiaries() {
        List<BeneficiaryDTO> beneficiaries = beneficiaryService.getAllBeneficiaries();
        return new ResponseEntity<>(beneficiaries, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiaryDTO> updateBeneficiary(@PathVariable("id") Integer id, @RequestBody BeneficiaryDTO beneficiaryDTO) {
        BeneficiaryDTO updatedBeneficiary = beneficiaryService.updateBeneficiary(id, beneficiaryDTO);
        return new ResponseEntity<>(updatedBeneficiary, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable("id") Integer id) {
        beneficiaryService.deleteBeneficiary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}