package com.example.esercizio_3_restful.entity;

import com.example.esercizio_3_restful.Enum.CarType;
import com.example.esercizio_3_restful.Enum.SlotType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Car {
    @Id
    private String plateCode;
    private CarType type;
    private SlotType slotType;
    private User user;
}