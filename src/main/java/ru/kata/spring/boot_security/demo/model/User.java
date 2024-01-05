
package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Введите имя")
    @Size(min = 1, max = 30, message = "Поле должно содержать от 1 до 30 символов")
    @Column(name = "first_name")
    private String username;
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 2, max = 30, message = "Поле должно содержать от 2 до 30 символов")
    @Column(name = "last_name")
    private String lastName;
    @Min(value = 1, message = "Введите корректный возраст")
    @Column(name = "age")
    private int age;
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;
    @NotEmpty
    @Size(min = 2, max = 200, message = "Поле должно содержать от 2 до 30 символов")
    @Column(name = "password")
    private String password;
    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(String username, String lastName, int age, String email, String password) {
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String rolesToString() {
        return roleSet.stream().map(Role::getAuthority).collect(Collectors.joining(" "));
    }


    public void setRoleSet(Role roleSet) {
        this.roleSet.add(roleSet);
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
