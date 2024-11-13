package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.BankEmployeeDTO;
import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.entity.BankEmployee;
import com.hexaware.MaverickBank.entity.Role;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.BankEmployeeRepository;
import com.hexaware.MaverickBank.repository.BankRepository;
import com.hexaware.MaverickBank.repository.RoleRepository;
import com.hexaware.MaverickBank.service.BankEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankEmployeeServiceImpl implements BankEmployeeService {

    private final BankEmployeeRepository bankEmployeeRepository;
    private final BankRepository bankRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public BankEmployeeServiceImpl(BankEmployeeRepository bankEmployeeRepository, BankRepository bankRepository, RoleRepository roleRepository) {
        this.bankEmployeeRepository = bankEmployeeRepository;
        this.bankRepository = bankRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public BankEmployeeDTO createEmployee(BankEmployeeDTO bankEmployeeDTO) {
        Bank bank = bankRepository.findById(bankEmployeeDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        Role role = roleRepository.findById(bankEmployeeDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        BankEmployee bankEmployee = new BankEmployee();
        bankEmployee.setFirstName(bankEmployeeDTO.getFirstName());
        bankEmployee.setLastName(bankEmployeeDTO.getLastName());
        bankEmployee.setEmail(bankEmployeeDTO.getEmail());
        bankEmployee.setPassword(bankEmployeeDTO.getPassword());
        bankEmployee.setBank(bank);
        bankEmployee.setRole(role);
        bankEmployee.setCreatedAt(LocalDateTime.now());
        bankEmployee.setUpdatedAt(LocalDateTime.now());

        BankEmployee savedEmployee = bankEmployeeRepository.save(bankEmployee);
        return convertToDTO(savedEmployee);
    }


    @Override
    public BankEmployeeDTO updateEmployee(Integer employeeId, BankEmployeeDTO bankEmployeeDTO) {
        BankEmployee bankEmployee = bankEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Bank bank = bankRepository.findById(bankEmployeeDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        Role role = roleRepository.findById(bankEmployeeDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        bankEmployee.setFirstName(bankEmployeeDTO.getFirstName());
        bankEmployee.setLastName(bankEmployeeDTO.getLastName());
        bankEmployee.setEmail(bankEmployeeDTO.getEmail());
        bankEmployee.setPassword(bankEmployeeDTO.getPassword());
        bankEmployee.setBank(bank);
        bankEmployee.setRole(role);
        bankEmployee.setUpdatedAt(LocalDateTime.now());

        BankEmployee updatedEmployee = bankEmployeeRepository.save(bankEmployee);
        return convertToDTO(updatedEmployee);
    }

    @Override
    public BankEmployeeDTO getEmployeeById(Integer employeeId) {
        BankEmployee bankEmployee = bankEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return convertToDTO(bankEmployee);
    }

    @Override
    public List<BankEmployeeDTO> getAllEmployees() {
        List<BankEmployee> employees = bankEmployeeRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        if (!bankEmployeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        bankEmployeeRepository.deleteById(employeeId);
    }

    private BankEmployeeDTO convertToDTO(BankEmployee bankEmployee) {
        BankEmployeeDTO dto = new BankEmployeeDTO();
        dto.setEmpid(bankEmployee.getEmployeeId());
        dto.setFirstName(bankEmployee.getFirstName());
        dto.setLastName(bankEmployee.getLastName());
        dto.setEmail(bankEmployee.getEmail());
        dto.setPassword(bankEmployee.getPassword());
        dto.setBankId(bankEmployee.getBank().getBankId());
        dto.setRoleId(bankEmployee.getRole().getRoleId());
        return dto;
    }
}