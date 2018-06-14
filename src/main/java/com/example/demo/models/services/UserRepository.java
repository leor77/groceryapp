package com.example.demo.models.services;

import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Customer, String> {
}
