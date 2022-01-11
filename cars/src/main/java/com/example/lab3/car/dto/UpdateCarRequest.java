package com.example.lab3.car.dto;

import com.example.lab3.car.entity.Car;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateCarRequest {

    private int maxSpeed;

    private int seats;

    private int horsePower;

    public static BiFunction<Car, UpdateCarRequest, Car> dtoToEntity() {
        return (car, request) -> {
            car.setMaxSpeed(request.getMaxSpeed());
            car.setSeats(request.getSeats());
            car.setHorsePower(request.getHorsePower());
            return car;
        };
    }
}
