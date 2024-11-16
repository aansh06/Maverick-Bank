package com.hexaware.MaverickBank.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(name = "account_type", nullable = false)
    private String accountType; // [savings, checking, business,]
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @Column(name = "status", nullable = false)
    private String status; // [ "Active", "Inactive","Under Verification" "Closed"]

//    private String branchName;
//    private String ifscCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount")
    private Set<Transaction> transactionsAsSource;

    @OneToMany(mappedBy = "destinationAccount")
    private Set<Transaction> transactionsAsDestination;

//    @OneToMany(mappedBy = "account")
//    private Set<Transaction> transactions;

    @ManyToMany
    @JoinTable(
            name = "account_beneficiary",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficiary_id")
    )
    private Set<Beneficiary> beneficiaries;

    public Account(){}

    public Account(Integer accountId, String accountType, String accountNumber, Double balance, String status, LocalDateTime createdAt, LocalDateTime updatedAt, Customer customer, Set<Transaction> transactionsAsSource, Set<Transaction> transactionsAsDestination) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customer = customer;
       // this.beneficiaries=beneficiaries;
        this.transactionsAsSource = transactionsAsSource;
        this.transactionsAsDestination = transactionsAsDestination;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

//    public String getBranchName() {
//        return branchName;
//    }
//
//    public void setBranchName(String branchName) {
//        this.branchName = branchName;
//    }
//
//    public String getIfscCode() {
//        return ifscCode;
//    }
//
//    public void setIfscCode(String ifscCode) {
//        this.ifscCode = ifscCode;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Transaction> getTransactionsAsSource() {
        return transactionsAsSource;
    }

    public void setTransactionsAsSource(Set<Transaction> transactionsAsSource) {
        this.transactionsAsSource = transactionsAsSource;
    }

    public Set<Transaction> getTransactionsAsDestination() {
        return transactionsAsDestination;
    }

    public void setTransactionsAsDestination(Set<Transaction> transactionsAsDestination) {
        this.transactionsAsDestination = transactionsAsDestination;
    }

    //    public Set<Transaction> getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(Set<Transaction> transactions) {
//        this.transactions = transactions;
//    }


//    public Set<Beneficiary> getBeneficiaries() {
//        return beneficiaries;
//    }
//
//    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
//        this.beneficiaries = beneficiaries;
//    }
}
