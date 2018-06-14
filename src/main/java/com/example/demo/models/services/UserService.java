package com.example.demo.models.services;

import com.example.demo.models.Cheese;
import com.example.demo.models.Customer;
import com.example.demo.models.Role;
import com.example.demo.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;

@Autowired
private CheeseDao cheeseDao;

public void createUser(Customer customer) {

    Role userRole = new Role("USER");
    List<Role> roles = new ArrayList<>();
    BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
    customer.setPassword(encoder.encode(customer.getPassword()));
    roles.add(userRole);
    customer.setRoles(roles);

    customer.setAccountFunds(new BigDecimal(250.00));

    userRepository.save(customer);
    }

    public void createAdmin(Customer customer) {

    Role userRole = new Role("ADMIN");
    List<Role> roles = new ArrayList<>();
    BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
    customer.setPassword(encoder.encode(customer.getPassword()));
    roles.add(userRole);
    customer.setRoles(roles);

    userRepository.save(customer);

    }

    public BigDecimal getCartTotal(Customer customer) {

        double total = 0;

        for (Cheese c : customer.getCheeses())
            total += c.getPrice().doubleValue();

        BigDecimal bd = new BigDecimal(Double.toString(total));

        return bd.setScale(2, RoundingMode.CEILING);
    }

    public Customer addToCart(Customer customer, int productId) {

        if (!customer.getCheeses().contains(cheeseDao.getCheeseById(productId))) {
            customer.getCheeses().add(cheeseDao.getCheeseById(productId));
        }

        else if (customer.getCheeses().contains(cheeseDao.getCheeseById(productId))) {
            customer.getCheeses().remove(cheeseDao.getCheeseById(productId));
        }

        return customer;
    }

}