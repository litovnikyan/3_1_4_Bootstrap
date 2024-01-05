package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DbInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DbInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void addUserInDb() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        User admin = new User("admin", "admin", 25, "admin@mail.ru", passwordEncoder.encode("admin"));
        User user = new User("user", "user", 45, "user@mail.ru", passwordEncoder.encode("user"));
        admin.setRoleSet(roleAdmin);
        user.setRoleSet(roleUser);

        userRepository.save(admin);
        userRepository.save(user);
    }
}