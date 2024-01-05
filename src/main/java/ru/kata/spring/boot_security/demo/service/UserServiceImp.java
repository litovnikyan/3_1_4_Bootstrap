package ru.kata.spring.boot_security.demo.service;

import com.mysql.cj.exceptions.OperationCancelledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Override
    public User findByName(String name) {
        return userRepository.findUserByUsername(name).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new OperationCancelledException("Такой пользователь уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        User newUser = userRepository.getById(user.getId());

        if (newUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        newUser.setUsername(user.getUsername());
        newUser.setLastName(user.getLastName());
        newUser.setAge(user.getAge());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoleSet(user.getRoleSet());
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

}





