package com.example.esercizio_3_restful.entity;

import com.example.esercizio_3_restful.Enum.SlotType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Slot {
    @Id
    @GeneratedValue
    private Long id;
    private SlotType type;
    private boolean full;
    private Garage garage;
    private int level;
}