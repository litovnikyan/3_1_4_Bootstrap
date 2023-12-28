package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.MyUserDetails;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository repository;

    public UserServiceImp(UserRepository userRepository, RoleRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepository.findByEmail(username);
        if (byUsername == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(byUsername);
    }
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    };


    public List<Role> getAllRoles(){
        return repository.findAll();
    }
}
