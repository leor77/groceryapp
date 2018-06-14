package com.example.demo.models.data;

import com.example.demo.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrdersDAO extends JpaRepository<Orders, Integer> {
    Orders findOrderByCustomerId(int customerId);
    List<Orders> findOrdersByCustomerId(int customerId);
}