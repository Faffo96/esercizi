package com.example.esercizio_3_restful.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private User user;
    private Car car;
    private Slot slot;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private static double hourPrice = 3;
    private static double monthlyPrice = 200;
    private boolean monthly;
    private double total;
}