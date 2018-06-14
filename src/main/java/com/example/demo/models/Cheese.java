package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cheese {

    @NotNull
    @Size(min=2, max=20)
    private String name;


    @NotNull
    @Size(min=2, max=20)
    private String description;

    @NotNull
    @DecimalMax("10000.0") @DecimalMin("0.0")
    private BigDecimal price;

    @Id
    @GeneratedValue
    private int id;

    private String type;


    @ManyToMany(mappedBy = "cheeses")
    private List<Customer> customers = new ArrayList<>();

//    @ManyToMany(mappedBy = "cheeses")
//    private List<Orders> orders = new ArrayList<>();

    public Cheese() {}

    public Cheese(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //    public List<Orders> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Orders> orders) {
//        this.orders = orders;
//    }
}
