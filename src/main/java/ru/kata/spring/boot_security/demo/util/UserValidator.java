package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.MyUserDetailsServiceImp;

@Component
public class UserValidator implements Validator {

    private final MyUserDetailsServiceImp myUserDetailsService;

    @Autowired
    public UserValidator(MyUserDetailsServiceImp user) {
        this.myUserDetailsService = user;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (myUserDetailsService.loadUserByUsername(user.getUsername()) != null) {//TODO
             errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
        }

    }
}
