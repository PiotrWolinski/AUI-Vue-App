package com.example.lab3.car.dto;

import com.example.lab3.car.entity.Car;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCarResponse {

    private Long id;

    private String name;

    private int wheels;

    private int seats;

    private int maxSpeed;

    private int horsePower;

    private double displacement;

    private int doors;

    private String user;

    public static Function<Car, GetCarResponse> entityToDto() {
        return car -> GetCarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .wheels(car.getWheels())
                .seats(car.getSeats())
                .maxSpeed(car.getMaxSpeed())
                .horsePower(car.getHorsePower())
                .displacement(car.getDisplacement())
                .doors(car.getDoors())
                .user(car.getUser().getLogin())
                .build();
    }
}
