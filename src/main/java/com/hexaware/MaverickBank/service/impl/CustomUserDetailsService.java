package com.hexaware.MaverickBank.service.impl;


import com.hexaware.MaverickBank.entity.BankEmployee;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.repository.BankEmployeeRepository;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final BankEmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(CustomerRepository customerRepository, BankEmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // for checking bankemployee
        BankEmployee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return new User(
                    employee.getEmail(),
                    employee.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(employee.getRole().getRoleName()))
            );
        }

        // for checking customer
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(customer.getRole().getRoleName()))
        );
    }
}