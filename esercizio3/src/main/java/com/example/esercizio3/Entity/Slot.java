package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.Role;
import com.example.esercizio3.Enum.SlotType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Slot {
    private static int counter;
    private int id;
    private SlotType type;
    private boolean full;
    private Garage garage;
    private int level;

    private Slot(SlotType type, Garage garage, int level) {
        this.id += counter;
        this.type = type;
        this.garage = garage;
        if (level <= garage.getLevels()) {
            this.level = level;
        } else throw new RuntimeException("Impossibile scegliere il piano " + level + ". Il garage possiede solo il piano terra (0) e " + garage.getLevels() + " piani.");
    }

    public static Slot createSlot(SlotType type, Garage garage, int level, User user) {
        if(user.getRole().equals(Role.ADMIN)) {
            Slot slot = new Slot(type, garage, level);
            List<Slot> slots = garage.getSlotList();
            slots.add(slot);
            garage.setSlotList(slots);
            return slot;
        }
        throw new RuntimeException("Solo un Admin puo' creare un Garage.");
    }

    public static List<Slot> getSlotByLevel (Garage garage, int level) {
        List<Slot> slots = garage.getSlotList();
        return slots.stream().filter(e -> e.level == level).collect(Collectors.toList());
    }

    public static List<Slot> getSlotByType(Garage garage, SlotType type) {
        List<Slot> slots = garage.getSlotList();
        return slots.stream().filter(e -> e.getType() == type).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", type=" + type +
                ", full=" + full +
                ", garage=" + garage +
                ", level=" + level +
                '}';
    }
}
