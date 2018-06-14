package com.example.demo.controllers;

import com.example.demo.models.Cheese;
import com.example.demo.models.Customer;
import com.example.demo.models.Orders;
import com.example.demo.models.Product;
import com.example.demo.models.data.CheeseDao;
import com.example.demo.models.data.CustomerDao;
import com.example.demo.models.data.OrdersDAO;
import com.example.demo.models.data.ProductDao;
import com.example.demo.models.services.ProductService;
import com.example.demo.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("cheese")
public class UserController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    UserService userService;

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;

    @RequestMapping(value = "login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("customer", new Customer());
        return "cheese/login";
    }

    @RequestMapping(value = "account")
    public String accountInfo(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        model.addAttribute("customer", customer);
        model.addAttribute("cheeses", customer.getCheeses());
        model.addAttribute("total", userService.getCartTotal(customer));

        return "cheese/account";
    }

    @PostMapping(value = "account")
    public String removeItem(@RequestParam int cheeseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        if (customer.getCheeses().contains(cheeseDao.getCheeseById(cheeseId))) {
            customer.getCheeses().remove(cheeseDao.getCheeseById(cheeseId));
        }

        customerDao.save(customer);

        return "redirect:/cheese/account";

    }

    @RequestMapping(value = "checkout")
    public String orderCheckout(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        model.addAttribute("cheeses", customer.getCheeses());
        model.addAttribute("total", userService.getCartTotal(customer));

        return "cheese/checkout";
    }

    @GetMapping("signup")
    public String displaySignUpForm(Model model) {
        model.addAttribute("title", "Sign Up");
        model.addAttribute("customer", new Customer());
        return "cheese/signup";
    }

    @PostMapping(value = "signup")
    public String processSignUp(Model model, @ModelAttribute Customer customer, Errors errors) {

        if (errors.hasErrors()) {
            return "cheese/signup";
        }

        userService.createUser(customer);
        return "cheese/success";
    }

    @GetMapping("ordersuccess")
    public String showForm() {
        return "cheese/ordersuccess";
    }


    @PostMapping("checkout")
    public String completeOrder() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        double accountFunds = customer.getAccountFunds().doubleValue();
        double cartTotal = userService.getCartTotal(customer).doubleValue();

        if (accountFunds >= cartTotal) {
            accountFunds = accountFunds - cartTotal;

        customer.setAccountFunds(new BigDecimal(accountFunds));

        Set<Product> products = new HashSet<>();

        for (Cheese c : customer.getCheeses()) {
            products.add(new Product(c.getName(), c.getPrice()));
        }

        Orders order = new Orders(products, customer.getAccountNumber(), new BigDecimal(cartTotal));
        customer.getCheeses().clear();

        customerDao.save(customer);
        ordersDAO.save(order);

        return "redirect:/cheese/ordersuccess";
        }

        else
            return "redirect:/cheese/orderfail";
//        return "redirect:/cheese/checkout";
    }

    @GetMapping("orders")
    public String viewOrderHistory(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        List<Orders> orders = ordersDAO.findOrdersByCustomerId(customer.getAccountNumber());
        model.addAttribute("orders", orders);

        return "cheese/orders";
    }
}