//package ru.kata.spring.boot_security.demo.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import ru.kata.spring.boot_security.demo.model.Role;
//public class GrantedAuthorityImp implements GrantedAuthority {
//    private Role role;
//    public GrantedAuthorityImp(Role role) {
//        this.role = role;
//    }
//    @Override
//    public String getAuthority() {
//        return this.role.getName();
//    }
//}
