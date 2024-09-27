package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Entity.Slot;
import lombok.Data;

import java.util.List;

@Data
public class GarageDTO {
    private Long id;
    private int levels;
    private List<Slot> slotList;
}
