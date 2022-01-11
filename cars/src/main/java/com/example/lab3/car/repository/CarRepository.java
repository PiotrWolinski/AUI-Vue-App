package com.example.lab3.car.repository;

import com.example.lab3.car.entity.Car;
import com.example.lab3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByIdAndUser(Long id, User user);

    List<Car> findAllByUser(User user);
}
