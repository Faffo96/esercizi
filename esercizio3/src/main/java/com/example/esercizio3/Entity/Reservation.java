package com.example.esercizio3.Entity;

import lombok.Data;

@Data
public class Reservation {
    private int id;
    private User user;
    private Car car;
    private Slot slot;
}
