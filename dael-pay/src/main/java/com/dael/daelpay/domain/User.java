package com.dael.daelpay.domain;

import com.dael.daelpay.dto.UserDto;
import com.sun.istack.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    protected User(){
    }

    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User makeUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        return new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
    }
}
