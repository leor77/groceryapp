package com.example.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
            joinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name = "CUSTOMER_EMAIL", referencedColumnName = "email")
    })
    private List<Customer> customers;


    public Role(String name, List<Customer> customers) {
        this.name = name;
        this.customers = customers;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}