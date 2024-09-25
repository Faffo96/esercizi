package com.example.esercizio3.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reservation {
    private static int counter;
    private int id;
    private User user;
    private Car car;
    private Slot slot;

    public Reservation(User user, Car car, Slot slot) {
        this.id += counter;
        this.user = user;
        this.car = car;
        this.slot = slot;
    }
}
