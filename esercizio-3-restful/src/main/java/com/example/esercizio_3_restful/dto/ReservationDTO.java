package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;
    private User user;
    private Car car;
    @NotNull
    private Slot slot;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private boolean monthly;
}
