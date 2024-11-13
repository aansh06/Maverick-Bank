package com.hexaware.MaverickBank.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<BankEmployee> employees;

    @OneToMany(mappedBy = "role")
    private Set<BankEmployee> customers;

    public Role(){}

    public Role(Integer roleId, String roleName, Set<BankEmployee> employees, Set<BankEmployee> customers) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.employees = employees;
        this.customers = customers;
    }

    public Set<BankEmployee> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<BankEmployee> customers) {
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



    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", employees=" + employees +
                '}';
    }
}
