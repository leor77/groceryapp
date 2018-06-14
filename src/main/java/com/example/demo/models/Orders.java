package com.example.demo.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Orders {

    @GeneratedValue
    @Id
    private int orderId;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="customer_orders",
            joinColumns={@JoinColumn(name="ORDER_ID", referencedColumnName = "orderId")},
            inverseJoinColumns={@JoinColumn(name="PRODUCT_ID", referencedColumnName="id")})
    private Set<Product> products = new HashSet<>();

    private int customerId;

    private BigDecimal totalPrice;

    private Date date;

    public Orders() {}

    public Orders(Set<Product> products, int customerId, BigDecimal totalPrice) {

        this.products = products;
        this.customerId = customerId;
        this.totalPrice = totalPrice;

        this.date = new Date();
    }

    private String getFormattedDate() {
        DateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm");
        return format.format(this.date);
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}