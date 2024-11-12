package com.hexaware.MaverickBank.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<BankEmployee> employees;

    @OneToMany(mappedBy = "role")
    private Set<Customer> customers;

    public Role(){}

    public Role(Integer roleId, String roleName, Set<BankEmployee> employees, Set<Customer> customers) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.employees = employees;
        this.customers = customers;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<BankEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<BankEmployee> employees) {
        this.employees = employees;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", employees=" + employees +
                ", customers=" + customers +
                '}';
    }
}
