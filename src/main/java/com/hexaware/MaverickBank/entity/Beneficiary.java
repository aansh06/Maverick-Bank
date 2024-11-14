package com.hexaware.MaverickBank.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

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
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public Beneficiary(){}

    public Beneficiary(Integer beneficiaryId, String beneficiaryName, String relationship, Customer customer) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.relationship = relationship;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "beneficiaryId=" + beneficiaryId +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", customer=" + customer +
                '}';
    }
}
