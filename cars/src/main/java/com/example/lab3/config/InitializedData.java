package com.example.lab3.config;

import com.example.lab3.car.entity.Car;
import com.example.lab3.car.service.CarService;
import com.example.lab3.user.entity.User;
import com.example.lab3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializedData {

    private final CarService carService;

    private final UserService userService;

    @Autowired
    public InitializedData(CarService carService,  UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @PostConstruct
    private synchronized void init() {
        this.initUsers();
        this.initCars();
    }


    private synchronized void initUsers() {
        User admin = User.builder()
                .login("admin")
                .build();

        User mat = User.builder()
                .login("CarWow")
                .build();

        User emptyUser = User.builder()
                .login("looser")
                .build();

        userService.create(admin);
        userService.create(mat);
        userService.create(emptyUser);
    }

    private synchronized void initCars() {
        Car audiA6 = Car.builder()
                .user(userService.find("CarWow").get())
                .doors(5)
                .name("Audi A6")
                .wheels(4)
                .seats(5)
                .maxSpeed(240)
                .horsePower(302)
                .displacement(3.0)
                .build();

        Car audiQ8 = Car.builder()
                .user(userService.find("admin").get())
                .doors(5)
                .name("Audi RSQ8")
                .wheels(4)
                .seats(5)
                .maxSpeed(280)
                .horsePower(600)
                .displacement(4.0)
                .build();

        Car opel = Car.builder()
                .user(userService.find("admin").get())
                .doors(5)
                .name("Nissan 370Z")
                .wheels(4)
                .seats(5)
                .maxSpeed(200)
                .horsePower(180)
                .displacement(2.0)
                .build();

        Car fabia = Car.builder()
                .user(userService.find("admin").get())
                .doors(3)
                .name("Skoda Fabia")
                .wheels(4)
                .seats(4)
                .maxSpeed(170)
                .horsePower(110)
                .displacement(1.6)
                .build();

        carService.create(audiA6);
        carService.create(audiQ8);
        carService.create(opel);
        carService.create(fabia);
    }
}
