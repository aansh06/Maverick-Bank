package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.BankEmployeeDTO;
import com.hexaware.MaverickBank.entity.BankEmployee;

import java.util.List;

public interface BankEmployeeService {
    BankEmployeeDTO createEmployee(BankEmployeeDTO bankEmployeeDTO);
    BankEmployeeDTO updateEmployee(Integer employeeId, BankEmployeeDTO bankEmployeeDTO);
    BankEmployeeDTO  getEmployeeById(Integer employeeId);
    List<BankEmployeeDTO> getAllEmployees();
    void deleteEmployee(Integer employeeId);
}
