package com.example.esercizio3.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Garage {
    private int id;
    private int levels;
    private List<Slot> slot;

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", levels=" + levels +
                '}';
    }
}
