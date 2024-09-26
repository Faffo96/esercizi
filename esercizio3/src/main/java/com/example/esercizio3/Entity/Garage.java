package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Garage {
    private static int counter;
    private int id;
    private int levels;
    private List<Slot> slotList = new ArrayList<>();

    private Garage(int levels) {
        this.id += counter;
        this.levels = levels;
        counter++;
    }

    public static Garage createGarage(int levels, User user) {
        if(user.getRole().equals(Role.ADMIN)) {
            return new Garage(levels);
        }
        throw new RuntimeException("Solo un Admin puo' creare un Garage.");
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", levels=" + levels +
                '}';
    }
}
