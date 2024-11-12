package com.hexaware.MaverickBank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "beneficiary")
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer beneficiaryId;

    private String beneficiaryName;
    private String relationship;

    @ManyToOne
    @JoinColumn(name = "customer_id")
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
