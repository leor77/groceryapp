package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    @GeneratedValue
    @Id
    private int accountNumber;

    private BigDecimal accountFunds;

    @NotNull
    @Size(min = 2)
    private String password;

    @NotNull
    @Size(min = 2, max = 25)
    @Email
    private String email;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
            joinColumns={@JoinColumn(name="CUSTOMER_EMAIL", referencedColumnName = "email")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")})
    private List<Role> roles;

    //@ElementCollection

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="cheese_customers",
            joinColumns={@JoinColumn(name="CUSTOMER_ID", referencedColumnName = "accountNumber")},
            inverseJoinColumns={@JoinColumn(name="PRODUCT_ID", referencedColumnName="id")})
    private List<Cheese> cheeses = new ArrayList<>();


    public Customer(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountFunds = new BigDecimal(225.00);
    }

    public Customer() {}

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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAccountNumber() {
        return accountNumber;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public BigDecimal getAccountFunds() {
        return accountFunds;
    }

    public void setAccountFunds(BigDecimal accountFunds) {
        this.accountFunds = accountFunds;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<Cheese> cheeses) {
        this.cheeses = cheeses;
    }
}