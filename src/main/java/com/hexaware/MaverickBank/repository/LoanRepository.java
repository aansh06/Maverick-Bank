package com.hexaware.MaverickBank.repository;

import com.hexaware.MaverickBank.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

}