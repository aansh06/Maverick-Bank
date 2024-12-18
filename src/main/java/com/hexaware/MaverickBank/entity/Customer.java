package com.hexaware.MaverickBank.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "customerId")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email",unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "aadhar_number",unique = true, nullable = false)
    private String aadharNumber;
    @Column(name = "pan_number",unique = true)
    private String panNumber;
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts;

    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans;


    @OneToMany(mappedBy = "linkedCustomer")
    private Set<Beneficiary> beneficiaries;

    public Customer(){}

    public Customer(Integer customerId, String firstName, String lastName, String email, String password, String contactNumber, String address, String aadharNumber, String panNumber, Date dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt, Bank bank, Role role, Set<Account> accounts, Set<Loan> loans, Set<Beneficiary> beneficiaries) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.address = address;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bank = bank;
        this.role = role;
        this.accounts = accounts;
        this.loans = loans;
        this.beneficiaries = beneficiaries;
    }

    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                ", accounts=" + accounts +
                ", loans=" + loans +
                ", beneficiaries=" + beneficiaries +
                '}';
    }
}
