package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO createLoan(LoanDTO loanDTO);
    LoanDTO getLoanById(Integer loanId);
    List<LoanDTO> getAllLoans();
    LoanDTO updateLoan(Integer loanId, LoanDTO loanDTO);
    void deleteLoan(Integer loanId);
    List<LoanDTO> getLoansByCustomerId(Integer customerId);
    Double calculateEmi(Integer loanId);
    Double getRemainingUnpaidAmount(Integer loanId);
    void payEmi(Integer loanId, Integer accountId);

}
