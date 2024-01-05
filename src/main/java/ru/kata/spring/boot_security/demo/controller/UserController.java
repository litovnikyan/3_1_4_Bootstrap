package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp myUserDetails) {
        this.userServiceImp = myUserDetails;
    }


    @GetMapping
    public String getUserInfo(Principal principal,  Model model){
        User user1 = userServiceImp.findByName(principal.getName());
        model.addAttribute("user", user1);
        return "user";
    }

}
