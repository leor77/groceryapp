package com.example.demo.models;

public enum CheeseType {

    Cheese ("Cheese"),
    Bakery ("Bakery"),
    Meat("Meat"),
    Drinks("Drinks"),
    Produce("Produce");

    public String name;

    CheeseType(String name) {
        this.name = name;
    }
}