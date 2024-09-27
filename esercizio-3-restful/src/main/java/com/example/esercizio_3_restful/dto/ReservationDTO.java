package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.entity.Car;
import com.example.esercizio_3_restful.entity.Slot;
import com.example.esercizio_3_restful.entity.User;

import java.time.LocalDateTime;

public class ReservationDTO {
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
