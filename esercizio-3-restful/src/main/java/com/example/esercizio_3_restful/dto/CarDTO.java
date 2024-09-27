package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Enum.CarType;
import com.example.esercizio_3_restful.Enum.SlotType;
import com.example.esercizio_3_restful.Entity.User;
import lombok.Data;

@Data
public class CarDTO {
    private String plateCode;
    private CarType type;
    private SlotType slotType;
    private User user;
}
