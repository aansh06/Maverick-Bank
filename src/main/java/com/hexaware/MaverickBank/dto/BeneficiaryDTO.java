package com.hexaware.MaverickBank.dto;

public class BeneficiaryDTO {
    private Integer beneficiaryId;
    private String name;
    private String relationship;
//    private String accountNumber;
    private Integer customerId;

    public BeneficiaryDTO(){}

    public BeneficiaryDTO(Integer beneficiaryId, String name, String relationship,  Integer customerId) {
        this.beneficiaryId = beneficiaryId;
        this.name = name;
        this.relationship = relationship;
//        this.accountNumber = accountNumber;
        this.customerId = customerId;
    }

    public Integer getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

//    public String getAccountNumber() {
//        return accountNumber;
//    }
//
//    public void setAccountNumber(String accountNumber) {
//        this.accountNumber = accountNumber;
//    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
