package com.hexaware.MaverickBank.entity;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Integer bankId;
    @Column(name = "bank_name", nullable = false)
    private String bankName;
    @Column(name = "address")
    private String address;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "bank")
//    private Set<BankEmployee> employees;
//
//    @OneToMany(mappedBy = "bank")
//    private Set<Customer> customers;

    public Bank(){}

//    public Bank(Integer bankId, String bankName, String address, String contactNumber, String email, LocalDateTime createdAt, LocalDateTime updatedAt, Set<BankEmployee> employees, Set<Customer> customers) {
//        this.bankId = bankId;
//        this.bankName = bankName;
//        this.address = address;
//        this.contactNumber = contactNumber;
//        this.email = email;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.employees = employees;
//        this.customers = customers;
//    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

//    public Set<BankEmployee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(Set<BankEmployee> employees) {
//        this.employees = employees;
//    }
//
//    public Set<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(Set<Customer> customers) {
//        this.customers = customers;
//    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
//                ", employees=" + employees +
//                ", customers=" + customers +
                '}';
    }
}
