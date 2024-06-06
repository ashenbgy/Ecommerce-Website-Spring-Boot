package com.ecom.site_project.controller;

import com.ecom.site_project.entity.*;
import com.ecom.site_project.exception.ProductNotFoundException;
import com.ecom.site_project.repository.CategoryRepository;
import com.ecom.site_project.service.ProductService;
import com.ecom.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRep;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listCategories", categoryRep.findAllEnabled());
        try {
            model.addAttribute("listProducts", productService.getRandomAmountOfProducts());
        } catch (ProductNotFoundException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "/error/404";
        }
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("listCategories", categoryRep.findAllEnabled());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, UserInfo userInfo) {
        user.setRole(Role.USER);
        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/basket")
    public String showShoppingCard(Model model,
                                   Principal principal) {
        if (principal != null) {
            List<OrderBasket> orderBaskets = userService.getUserByLogin(principal.getName()).getOrderBaskets();
            model.addAttribute("orderBaskets", orderBaskets);
            model.addAttribute("order", new Order());
            model.addAttribute("listCategories", categoryRep.findAllEnabled());
        } else {
            model.addAttribute("error", new NotFoundException("Order basket was not found"));
            return "/error/404";
        }
        return "shopping-cart";
    }

    @GetMapping("/category")
    public String showCategories(Model model) {
        List<Category> listEnabledCategories = categoryRep.findAllEnabled();
        model.addAttribute("listCategories", listEnabledCategories);
        return "category";
    }

    @GetMapping("/contact")
    public String showContact(Model model) {
        model.addAttribute("listCategories", categoryRep.findAllEnabled());
        return "contact";
    }

    @GetMapping("/products")
    public String showProduct(Model model) throws ProductNotFoundException {
        model.addAttribute("listCategories", categoryRep.findAllEnabled());
        try {
            model.addAttribute("listProducts", productService.getAllProducts());
        } catch (ProductNotFoundException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "/error/404";
        }
        return "products";
    }

}
