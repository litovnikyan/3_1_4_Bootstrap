package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.FetchType;
import javax.transaction.Transactional;

@Component
public class DbInitializer {
    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DbInitializer(UserRepository userRepository, RoleRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @PostConstruct
    @Transactional
    public void addUserInDb() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User admin = new User("admin", 25, passwordEncoder.encode("admin"), "admin", "admin");
        User user = new User("user", 20, passwordEncoder.encode("user"), "user", "user");
        admin.setRoleSet(roleAdmin);
        user.setRoleSet(roleUser);

        userRepository.save(admin);
        userRepository.save(user);
    }
}