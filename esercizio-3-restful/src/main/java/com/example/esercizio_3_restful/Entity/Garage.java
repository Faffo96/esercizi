package com.example.esercizio_3_restful.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Garage {
    @Id
    @GeneratedValue
    private Long id;
    private int levels;
    private List<Slot> slotList;
}