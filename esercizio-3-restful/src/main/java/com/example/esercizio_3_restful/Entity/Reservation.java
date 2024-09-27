package com.example.esercizio_3_restful.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private static double hourPrice = 3;
    private static double monthlyPrice = 200;
    private boolean monthly;
    private double total;
}