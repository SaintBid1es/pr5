package com.mpt.practyp.controller;

import com.mpt.practyp.model.Customer;
import com.mpt.practyp.model.Product;
import com.mpt.practyp.repository.CustomerRepository;
import com.mpt.practyp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/user/products")
    public String showProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "user/products";
    }

    @GetMapping("/user/profile")
    public String showProfile(Model model, Principal principal) {
        Customer customer = customerRepository.findByUsername(principal.getName()).orElse(null);
        model.addAttribute("customer", customer);
        return "user/profile";
    }

    @GetMapping("/user/profile/edit")
    public String editProfile(Model model, Principal principal) {
        Customer customer = customerRepository.findByUsername(principal.getName()).orElse(null);
        model.addAttribute("customer", customer);
        return "user/edit_profile";
    }

    @PostMapping("/user/profile/edit")
    public String updateProfile(@ModelAttribute("customer") Customer customerForm, Principal principal) {
        Customer customer = customerRepository.findByUsername(principal.getName()).orElse(null);
        if (customer != null) {
            customer.setFirstName(customerForm.getFirstName());
            customer.setLastName(customerForm.getLastName());
            customer.setEmail(customerForm.getEmail());
            customerRepository.save(customer);
        }
        return "redirect:/user/profile";
    }
}