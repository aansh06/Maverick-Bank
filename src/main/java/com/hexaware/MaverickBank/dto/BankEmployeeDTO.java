package com.hexaware.MaverickBank.dto;

public class BankEmployeeDTO {
    private int empid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer bankId;
    private Integer roleId;

    public BankEmployeeDTO(){}

    public BankEmployeeDTO(int empid,String firstName, String lastName, String email, String password, Integer bankId, Integer roleId) {
        this.empid=empid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bankId = bankId;
        this.roleId = roleId;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "BankEmployeeDTO{" +
                "empid=" + empid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bankId=" + bankId +
                ", roleId=" + roleId +
                '}';
    }
}
