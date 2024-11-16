package com.hexaware.MaverickBank.service.impl;

import com.hexaware.MaverickBank.dto.LoanDTO;
import com.hexaware.MaverickBank.entity.Account;
import com.hexaware.MaverickBank.entity.Customer;
import com.hexaware.MaverickBank.entity.Loan;
import com.hexaware.MaverickBank.globalexception.InsufficientFundsException;
import com.hexaware.MaverickBank.globalexception.ResourceNotFoundException;
import com.hexaware.MaverickBank.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, CustomerRepository customerRepository,AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.accountRepository=accountRepository;
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

    @Override
    public List<LoanDTO> getLoansByCustomerId(Integer customerId) {
        List<Loan> loans = loanRepository.findByCustomer_CustomerId(customerId);
        return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Double calculateEmi(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Double principal = loan.getPrincipalAmount();
        Double annualInterestRate = loan.getInterestRate();
        Integer tenureMonths = loan.getDuration();

        Double monthlyInterestRate = annualInterestRate / (12 * 100);
        Double emi = principal*monthlyInterestRate*(Math.pow(1 + monthlyInterestRate, tenureMonths))/(Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

        return emi;
    }

    @Override
    public Double getRemainingUnpaidAmount(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        return loan.getBalance();
    }

    @Override
    public void payEmi(Integer loanId, Integer accountId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Double principal = loan.getPrincipalAmount();
        Double annualInterestRate = loan.getInterestRate();
        Integer tenureMonths = loan.getDuration();

        Double monthlyInterestRate = annualInterestRate / (12 * 100);
        Double emi = principal*monthlyInterestRate*(Math.pow(1 + monthlyInterestRate, tenureMonths))/(Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);



        if (account.getBalance()-emi < 0) {
            throw new InsufficientFundsException("Insufficient balance in account to pay EMI");
        }


        account.setBalance(account.getBalance() - emi);
        accountRepository.save(account);


        loan.setBalance(loan.getBalance() - emi);


        if (loan.getBalance() <= 0) {
            loan.setBalance(0.0);
            loan.setStatus("Closed");
        }

        loanRepository.save(loan);
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