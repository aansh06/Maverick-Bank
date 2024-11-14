package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.LoanDTO;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.entity.Loan;
import com.hexaware.MaverickBank.exception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.CustomerRepository;
import com.hexaware.MaverickBank.repository.LoanRepository;
import com.hexaware.MaverickBank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Customer customer = customerRepository.findById(loanDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Loan loan = new Loan();
        loan.setLoanType(loanDTO.getLoanType());
        loan.setPrincipalAmount(loanDTO.getPrincipalAmount());
        loan.setInterestRate(loanDTO.getInterestRate());
        loan.setDuration(loanDTO.getDuration());
        loan.setStartDate(loanDTO.getStartDate() != null ? loanDTO.getStartDate() : LocalDate.now());
        loan.setStatus("Pending"); // Default status on creation
        loan.setCreatedAt(LocalDate.now());
        loan.setUpdatedAt(LocalDate.now());
        loan.setBalance(loanDTO.getPrincipalAmount().doubleValue());
        loan.setCustomer(customer);

        Loan savedLoan = loanRepository.save(loan);
        return convertToDTO(savedLoan);
    }

    @Override
    public LoanDTO getLoanById(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        return convertToDTO(loan);
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public LoanDTO updateLoan(Integer loanId, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Customer customer = customerRepository.findById(loanDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        loan.setLoanType(loanDTO.getLoanType());
        loan.setPrincipalAmount(loanDTO.getPrincipalAmount());
        loan.setInterestRate(loanDTO.getInterestRate());
        loan.setDuration(loanDTO.getDuration());
        loan.setStartDate(loanDTO.getStartDate());
        loan.setStatus(loanDTO.getStatus());
        loan.setUpdatedAt(LocalDate.now());
        loan.setBalance(loanDTO.getBalance());
        loan.setCustomer(customer);

        Loan updatedLoan = loanRepository.save(loan);
        return convertToDTO(updatedLoan);
    }

    @Override
    public void deleteLoan(Integer loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new ResourceNotFoundException("Loan not found");
        }
        loanRepository.deleteById(loanId);
    }

    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setLoanId(loan.getLoanId());
        dto.setLoanType(loan.getLoanType());
        dto.setPrincipalAmount(loan.getPrincipalAmount());
        dto.setInterestRate(loan.getInterestRate());
        dto.setDuration(loan.getDuration());
        dto.setStartDate(loan.getStartDate());
        dto.setStatus(loan.getStatus());
        dto.setCreatedAt(loan.getCreatedAt());
        dto.setUpdatedAt(loan.getUpdatedAt());
        dto.setBalance(loan.getBalance());
        dto.setCustomerId(loan.getCustomer().getCustomerId());
        return dto;
    }
}