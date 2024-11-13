package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Integer customerId, CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Integer customerId);
    List<CustomerDTO> getAllCustomers();
    void deleteCustomer(Integer customerId);
}
