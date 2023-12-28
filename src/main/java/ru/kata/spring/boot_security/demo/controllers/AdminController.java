package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserValidator userValidator;
    private RegistrationService registrationService;
    private UserService userService;
    private RoleRepository repository;

    @Autowired
    public AdminController(UserValidator userValidator, RegistrationService registrationService, UserService userService, RoleRepository repository) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userService = userService;
        this.repository = repository;
    }

    @GetMapping
    public String adminPage(){
        return "admin";
    }
    @GetMapping("/all-users")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/all-users";
    }

    @GetMapping("/add-user")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/add-user";
    }
    @PostMapping("/add-user")
    public String performRegistration(@ModelAttribute("user")@Valid User user, BindingResult bindingResult, @RequestParam("checkRoles") String[] role) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/add-user";
        }
        Set<Role> rs = new HashSet<>();
        for (String s : role) {
            rs.add(repository.findRoleByName("ROLE_" + s));
        }
        user.setRoleSet(rs);
        registrationService.register(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/update-user")
    public String updateUserForm(ModelMap model, @RequestParam("id")int id){
        User user =  userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoleSet());

        return "/admin/update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") User user, String[] role){
        Set<Role> rs = new HashSet<>();
        for (String s : role) {
            rs.add(new Role(s));
        }
        user.setRoleSet(rs);
        registrationService.register(user);

        return "redirect:/admin/all-users";
    }

    @PostMapping("/delete-user")
    public String deleteUserForm(@RequestParam("id") int id){
        userService.deleteUser(id);
        return "redirect:/admin/all-users";
    }

}
