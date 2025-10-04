package com.mpt.practyp.controller;

import com.mpt.practyp.repository.CustomerRepository;
import com.mpt.practyp.repository.ProductRepository;
import com.mpt.practyp.repository.CategoryRepository;
import com.mpt.practyp.repository.TagRepository;
import com.mpt.practyp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/")
    public String adminDashboard(Model model) {
        long customerCount = customerRepository.count();
        long productCount = productRepository.count();
        long categoryCount = categoryRepository.count();
        long tagCount = tagRepository.count();
        long profileCount = profileRepository.count();
        long totalCount = customerCount + productCount + categoryCount + tagCount + profileCount;

        model.addAttribute("customerCount", customerCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("tagCount", tagCount);
        model.addAttribute("profileCount", profileCount);
        model.addAttribute("totalCount", totalCount);

        return "index";
    }

}