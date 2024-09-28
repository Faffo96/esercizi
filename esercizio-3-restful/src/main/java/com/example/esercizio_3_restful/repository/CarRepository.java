package com.example.esercizio_3_restful.repository;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    public Optional<Car> findByPlateCode(String plateCode);
}
