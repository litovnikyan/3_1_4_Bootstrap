package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.Role;

public class RoleDetails implements GrantedAuthority {
    private Role role;

    public RoleDetails(Role role) {
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return this.role.getName();
    }
}
