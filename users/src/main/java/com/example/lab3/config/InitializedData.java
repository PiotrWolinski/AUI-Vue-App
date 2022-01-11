package com.example.lab3.config;

import com.example.lab3.digest.Sha256Utility;
import com.example.lab3.user.entity.User;
import com.example.lab3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class InitializedData {

    private final UserService userService;

    @Autowired
    public InitializedData(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private synchronized void initUsers() {
        User admin = User.builder()
                .login("admin")
                .name("John")
                .surname("Snow")
                .birthDate(LocalDate.of(2000, 2, 23))
                .email("john.snow@gmail.com")
                .password(Sha256Utility.hash("admin"))
                .build();

        User mat = User.builder()
                .login("CarWow")
                .name("Mat")
                .surname("Watson")
                .birthDate(LocalDate.of(1984, 6, 12))
                .email("mat@carwow.com")
                .password(Sha256Utility.hash("sTR0nG-p4ssW0rD"))
                .build();

        User emptyUser = User.builder()
                .login("looser")
                .name("John")
                .surname("Hill")
                .birthDate(LocalDate.of(2002, 6, 12))
                .email("john@hill.com")
                .password(Sha256Utility.hash("sTR0nG-p4ssW0rD"))
                .build();

        userService.create(admin);
        userService.create(mat);
        userService.create(emptyUser);
    }
}
