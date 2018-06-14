package com.example.demo.models.data;

import com.example.demo.models.Cheese;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheeseDao extends JpaRepository<Cheese, Integer> {
    Cheese getCheeseById(int id);
}
