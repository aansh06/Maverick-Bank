package com.hexaware.MaverickBank.repository;

import com.hexaware.MaverickBank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySourceAccount_AccountIdOrDestinationAccount_AccountId(Integer sourceAccountId, Integer destinationAccountId);


    // for credit transaction
    List<Transaction> findByTransactionTypeAndSourceAccount_AccountIdOrTransactionTypeAndDestinationAccount_AccountId(
            String depositType, Integer sourceAccountId, String transferType, Integer destinationAccountId);

    // for debit transaction
    List<Transaction> findByTransactionTypeAndSourceAccount_AccountIdOrTransactionTypeAndSourceAccount_AccountId(
            String depositType, Integer sourceAccountId, String transferType, Integer SourceAccountId);

}
