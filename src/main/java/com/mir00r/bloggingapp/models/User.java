package com.mir00r.bloggingapp.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author mir00r on 2019-09-20
 * @project IntelliJ IDEA
 */
@Entity
@Table(name = "user")
public class User extends BaseModel {
    private static final long serialVersionUID = 1L;

    private String username;
    private String avatar;

    @Column(name = "email")
    @Email(message = "*Please enter a valid email address!")
    @NotEmpty(message = "*Please provide an email! This field can not be empty!")
    private String email;

    @Column(name = "password")
    @Length(min = 3, message = "*Your password can not be less than 3 characters!")
    @NotEmpty(message = "*Please provide your password! This field can not be empty!")
    @org.springframework.data.annotation.Transient

    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name! This field can not be empty!")
    private String name;

    @Column(name = "active")
    private int active;

    @ManyToOne
    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
