package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.entity.BankEmployee;

import java.util.List;

public interface BankEmployeeService {
    BankEmployee createEmployee(BankEmployee bankEmployee);
    BankEmployee updateEmployee(Integer employeeId, BankEmployee bankEmployee);
    BankEmployee getEmployeeById(Integer employeeId);
    List<BankEmployee> getAllEmployees();
    void deleteEmployee(Integer employeeId);
}
