package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Enum.SlotType;
import com.example.esercizio_3_restful.entity.Garage;

public class SlotDTO {
    private Long id;
    private SlotType type;
    private boolean full;
    private Garage garage;
    private int level;
}
