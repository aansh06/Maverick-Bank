package com.hexaware.MaverickBank.controller;


import com.hexaware.MaverickBank.dto.BankEmployeeDTO;
import com.hexaware.MaverickBank.entity.BankEmployee;
import com.hexaware.MaverickBank.service.BankEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankEmployees")
public class BankEmployeeController {

    private final BankEmployeeService bankEmployeeService;

    @Autowired
    public BankEmployeeController(BankEmployeeService bankEmployeeService) {
        this.bankEmployeeService = bankEmployeeService;
    }

    @PostMapping
    public ResponseEntity<BankEmployeeDTO> createEmployee(@RequestBody BankEmployeeDTO bankEmployeeDTO) {
        BankEmployeeDTO  createdEmployee = bankEmployeeService.createEmployee(bankEmployeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }




    @GetMapping("/{id}")
    public ResponseEntity<BankEmployeeDTO> getEmployeeById(@PathVariable("id") Integer id) {
        BankEmployeeDTO bankEmployee = bankEmployeeService.getEmployeeById(id);
        return new ResponseEntity<>(bankEmployee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BankEmployeeDTO>> getAllEmployees() {
        List<BankEmployeeDTO> employees = bankEmployeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankEmployeeDTO> updateEmployee(@PathVariable("id") Integer id, @RequestBody BankEmployeeDTO bankEmployeeDTO) {
        BankEmployeeDTO updatedEmployee = bankEmployeeService.updateEmployee(id, bankEmployeeDTO);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id) {
        bankEmployeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}