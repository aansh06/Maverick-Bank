package com.hexaware.MaverickBank.dto;

import java.util.Set;

public class BeneficiaryDTO {
    private Integer beneficiaryId;
    private String name;
    private String relationship;
//    private String accountNumber;
    private Integer linkedCustomerId;
    private Set<Integer> accountIds;

    public BeneficiaryDTO(){}

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

    public Integer getLinkedCustomerId() {
        return linkedCustomerId;
    }

    public void setLinkedCustomerId(Integer linkedCustomerId) {
        this.linkedCustomerId = linkedCustomerId;
    }

    public Set<Integer> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(Set<Integer> accountIds) {
        this.accountIds = accountIds;
    }
}
