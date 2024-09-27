package com.example.esercizio_3_restful.repository;

import com.example.esercizio_3_restful.Entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}