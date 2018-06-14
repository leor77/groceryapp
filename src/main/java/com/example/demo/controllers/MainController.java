package com.example.demo.controllers;

import com.example.demo.models.Cheese;
import com.example.demo.models.CheeseType;
import com.example.demo.models.Customer;
import com.example.demo.models.data.CheeseDao;
import com.example.demo.models.data.CustomerDao;
import com.example.demo.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "cheese")
@Controller
public class MainController {

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    UserService userService;

    @RequestMapping(value = "")
    public String hello(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        model.addAttribute("total", userService.getCartTotal(customer));
        model.addAttribute("cart", customer.getCheeses());
        model.addAttribute("cheeses", cheeseDao.findAll());

        return "cheese/index";
    }

    @GetMapping("add")
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute("cheese", new Cheese());
        model.addAttribute("cheeseTypes", CheeseType.values());

        return "cheese/add";
    }

    @PostMapping("add")
    public String processAddCheeseForm(Model model,
                                       @ModelAttribute @Valid Cheese cheese,
                                       Errors errors) {
        if (errors.hasErrors()) {
            return "cheese/add";
        }

        cheeseDao.save(cheese);
        return "redirect:";
    }


    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(Model model, @RequestParam int[] cheeseIds) {
        for (int id : cheeseIds) {
            cheeseDao.deleteById(id);
        }
        return "redirect:";
    }

    @PostMapping(value = "")
    public String addItem(@RequestParam int cheeseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerDao.findByEmail(authentication.getName());

        customerDao.save(userService.addToCart(customer, cheeseId));

        return "redirect:";
    }
}