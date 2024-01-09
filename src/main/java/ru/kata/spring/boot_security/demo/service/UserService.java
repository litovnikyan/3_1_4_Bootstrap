package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface UserService{
    User findByName(String name);
    List<User> getAllUsers();
    User findUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    List<Role> findAll();
    void addRole(Role role);






}
