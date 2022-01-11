package com.example.lab3.car.controller;

import com.example.lab3.car.entity.Car;
import com.example.lab3.car.service.CarService;
import com.example.lab3.car.dto.CreateCarRequest;
import com.example.lab3.car.dto.GetCarResponse;
import com.example.lab3.car.dto.GetCarsResponse;
import com.example.lab3.user.entity.User;
import com.example.lab3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/users/{username}/cars")
public class UserCarController {

    private CarService carService;

    private UserService userService;

    @Autowired
    public UserCarController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GetCarsResponse> getCars(@PathVariable("username") String username) {
        Optional<User> user = userService.find(username);
        return user.map(val -> ResponseEntity.ok(GetCarsResponse.entityToDto().apply(carService.findAll(val))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<GetCarResponse> getCar(@PathVariable("username") String username,
                                                 @PathVariable("id") long id) {
        return carService.find(id, username).map(val -> ResponseEntity.ok(GetCarResponse.entityToDto().apply(val)))
                .orElseGet(() -> ResponseEntity.notFound().build()); // implement JPA
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@PathVariable("username") String username,
                                          @RequestBody CreateCarRequest request,
                                          UriComponentsBuilder builder) {
        Optional<User> user = userService.find(username);

        if (user.isPresent()) {
            Car car = CreateCarRequest
                    .dtoToEntity(name -> userService.find(username).orElseThrow())
                    .apply(request);
            car = carService.create(car); // Implement JPA

            return ResponseEntity.created(builder.pathSegment("api", "users", "{username}", "cars", "{id}")
                    .buildAndExpand(user.get().getLogin(), car.getId()).toUri()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("username") String username,
                                          @PathVariable("id") long id) {
        Optional<Car> car = carService.find(id, username);

        if (car.isPresent()) {
            carService.delete(car.get().getId()); // Implement JPA
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
