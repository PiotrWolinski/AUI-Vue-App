package com.example.lab3.car.dto;

import com.example.lab3.car.entity.Car;
import com.example.lab3.user.entity.User;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateCarRequest {

    private Long id;

    private String name;

    private int wheels;

    private int seats;

    private int maxSpeed;

    private int horsePower;

    private double displacement;

    private int doors;

    private String user;

    public static Function<CreateCarRequest, Car> dtoToEntity(
            Function<String, User> userFunction) {
        return request -> Car.builder()
                .id(request.getId())
                .name(request.getName())
                .wheels(request.getWheels())
                .seats(request.getSeats())
                .maxSpeed(request.getMaxSpeed())
                .horsePower(request.getHorsePower())
                .displacement(request.getDisplacement())
                .doors(request.getDoors())
                .user(userFunction.apply(request.getUser()))
                .build();
    }

}
