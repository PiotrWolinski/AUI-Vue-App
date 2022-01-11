package com.example.lab3.car.controller;

import com.example.lab3.car.entity.Car;
import com.example.lab3.car.service.CarService;
import com.example.lab3.car.dto.CreateCarRequest;
import com.example.lab3.car.dto.GetCarResponse;
import com.example.lab3.car.dto.GetCarsResponse;
import com.example.lab3.car.dto.UpdateCarRequest;
import com.example.lab3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/cars")
public class CarController {

    private CarService carService;

    private UserService userService;

    // add all params constructor and remove autowired
    @Autowired
    public CarController(CarService carService,UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GetCarsResponse> getCars() {
        List<Car> cars = carService.findAll();
        Function<Collection<Car>, GetCarsResponse> mapper = GetCarsResponse.entityToDto();
        GetCarsResponse response = mapper.apply(cars);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetCarResponse> getCar(@PathVariable("id") long id) {
        return carService.find(id)
                .map(car -> ResponseEntity.ok(GetCarResponse.entityToDto().apply(car)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody CreateCarRequest request, UriComponentsBuilder builder) {
        Car car = CreateCarRequest
                .dtoToEntity(user -> userService.find(user).orElseThrow())
                .apply(request);
        car = carService.create(car); // Service + Repository must be changed to meet JPA standard first for it to work

        return ResponseEntity.created(builder.pathSegment("api", "cars", "{id}")
                .buildAndExpand(car.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") long id) {
        Optional<Car> car = carService.find(id);

        if (car.isPresent()) {
            carService.delete(car.get().getId()); // not working due to wrong service implementation
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCar(@RequestBody UpdateCarRequest request,
                                          @PathVariable("id") long id) {
        Optional<Car> car = carService.find(id);

        if (car.isPresent()) {
            UpdateCarRequest.dtoToEntity().apply(car.get(), request);
            carService.update(car.get());

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
