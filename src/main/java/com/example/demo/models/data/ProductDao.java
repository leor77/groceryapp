package com.example.demo.models.data;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductDao extends JpaRepository<Product, Integer> {
    Product getProductByName(String name);
}
