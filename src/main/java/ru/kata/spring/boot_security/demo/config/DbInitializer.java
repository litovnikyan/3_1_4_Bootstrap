package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DbInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public DbInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostConstruct
    @Transactional
    public void addUserInDb() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        userService.addRole(roleAdmin);
        userService.addRole(roleUser);

        Set<Role> adminRoleSet = Stream.of(roleAdmin, roleUser).collect(Collectors.toSet());
        Set<Role> userRoleSet = Stream.of(roleUser).collect(Collectors.toSet());

        User admin = new User("admin", "admin", 25, "admin@mail.ru", passwordEncoder.encode("admin"), adminRoleSet);
        User user = new User("user", "user", 45, "user@mail.ru", passwordEncoder.encode("user"), userRoleSet);

        userRepository.save(admin);
        userRepository.save(user);
    }
}