package com.hexaware.MaverickBank.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "beneficiary")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "beneficiaryId")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer beneficiaryId;

    @Column(name = "name", nullable = false)
    private String beneficiaryName;

    @Column(name = "relationship", nullable = false)
    private String relationship; //["Spouse", "Sibling", "Friend"]

//    @Column(name = "account_number", nullable = false, unique = true)
//    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "linked_customer_id")
    private Customer linkedCustomer;

    @ManyToMany(mappedBy = "beneficiaries")
    private Set<Account> accounts;


    public Beneficiary(){}

    public Beneficiary(Integer beneficiaryId, String beneficiaryName, String relationship, Customer linkedCustomer,Set<Account> accounts) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.relationship = relationship;
        this.linkedCustomer = linkedCustomer;
        this.accounts=accounts;
    }

    public Integer getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Customer getLinkedCustomer() {
        return linkedCustomer;
    }

    public void setLinkedCustomer(Customer linkedCustomer) {
        this.linkedCustomer = linkedCustomer;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "beneficiaryId=" + beneficiaryId +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", linkedCustomer=" + linkedCustomer +
                ", accounts=" + accounts +
                '}';
    }
}
