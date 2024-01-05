package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserValidator userValidator;
    private UserService userService;


    @Autowired
    public AdminController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authUser", userService.findByName(principal.getName()));
        model.addAttribute("roles", userService.findAll());
        model.addAttribute("newUser", new User());
    return "/admin";
    }
    @PostMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user")User user){
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @PostMapping
    public String saveUser(@ModelAttribute("newUser")User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
