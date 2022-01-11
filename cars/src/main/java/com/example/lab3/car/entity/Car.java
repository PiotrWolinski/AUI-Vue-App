package com.example.lab3.car.entity;

import com.example.lab3.user.entity.User;
import com.example.lab3.vehicleEntity.Vehicle;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
public class Car extends Vehicle {

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private int doors;

    private double displacement;

    private int horsePower;

    @Override
    public String toString() {
        String s = "Car<";

        s += String.format("id=%d, ", getId());
        s += String.format("name=%s, ", getName());
        s += String.format("wheels=%d,", getWheels());
        s += String.format("seats=%s, ", getSeats());
        s += String.format("masSpeed=%s, ", getMaxSpeed());
        s += String.format("displacement=%s, ", getDisplacement());
        s += String.format("horsePower=%s, ", getHorsePower());
        s += String.format("user=%s, ", getUser().getLogin());
        s += ">";
        return s;
    }
}
