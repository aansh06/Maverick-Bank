package com.hexaware.MaverickBank.repository;

import com.hexaware.MaverickBank.entity.BankEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankEmployeeRepository extends JpaRepository<BankEmployee, Integer> {
    Optional<BankEmployee> findByEmail(String email);
}