package com.example.demo.models.data;


import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
}