package com.mpt.practyp.controller;

import com.mpt.practyp.model.Customer;
import com.mpt.practyp.model.Profile;
import com.mpt.practyp.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("customer") Customer customer,
                           BindingResult bindingResult,
                           @RequestParam(required = false) String address,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (repository.findByUsername(customer.getUsername()).isPresent()) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "register";
        }

        if (repository.findByEmail(customer.getEmail()).isPresent()) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "register";
        }

        Profile profile = new Profile();
        profile.setCustomer(customer);
        profile.setAddress(address != null && !address.trim().isEmpty() ? address.trim() : "Адрес не указан");
        profile.setDeleted(false);

        customer.setProfile(profile);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole("USER");
        customer.setDeleted(false);

        repository.save(customer);

        return "redirect:/login?success";
    }
}