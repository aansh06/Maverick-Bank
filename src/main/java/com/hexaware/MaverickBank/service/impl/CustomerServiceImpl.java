package com.hexaware.MaverickBank.service.impl;


import com.hexaware.MaverickBank.dto.CustomerDTO;
import com.hexaware.MaverickBank.entity.Bank;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.entity.Role;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.BankRepository;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import com.hexaware.MaverickBank.repository.RoleRepository;
import com.hexaware.MaverickBank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BankRepository bankRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BankRepository bankRepository, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.bankRepository = bankRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Bank bank = bankRepository.findById(customerDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        Role role = roleRepository.findById(customerDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        customer.setContactNumber(customerDTO.getContactNumber());
        customer.setAddress(customerDTO.getAddress());
        customer.setAadharNumber(customerDTO.getAadharNumber());
        customer.setPanNumber(customerDTO.getPanNumber());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setBank(bank);
        customer.setRole(role);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Bank bank = bankRepository.findById(customerDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        Role role = roleRepository.findById(customerDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        customer.setContactNumber(customerDTO.getContactNumber());
        customer.setAddress(customerDTO.getAddress());
        customer.setAadharNumber(customerDTO.getAadharNumber());
        customer.setPanNumber(customerDTO.getPanNumber());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setBank(bank);
        customer.setRole(role);
        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return convertToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }


    private CustomerDTO convertToDTO(Customer customer) {
            CustomerDTO dto = new CustomerDTO();
            dto.setCustId(customer.getCustomerId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setEmail(customer.getEmail());
            dto.setPassword(customer.getPassword());
            dto.setContactNumber(customer.getContactNumber());
            dto.setAddress(customer.getAddress());
            dto.setAadharNumber(customer.getAadharNumber());
            dto.setPanNumber(customer.getPanNumber());
            dto.setDateOfBirth(customer.getDateOfBirth());
            dto.setBankId(customer.getBank().getBankId());
            dto.setRoleId(customer.getRole().getRoleId());
            return dto;
    }

}
