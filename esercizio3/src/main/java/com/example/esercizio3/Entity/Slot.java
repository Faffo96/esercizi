package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.Role;
import com.example.esercizio3.Enum.SlotType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Slot {
    private static int counter;
    private int id;
    private SlotType type;
    private boolean full;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private Garage garage;
    private int level;

    private Slot(SlotType type, LocalDateTime date, LocalDateTime endDate, Garage garage, int level) {
        this.id += counter;
        this.type = type;
        this.full = false;
        this.date = date;
        this.endDate = endDate;
        this.garage = garage;
        if (level <= garage.getLevels()) {
            this.level = level;
        } else throw new RuntimeException("Impossibile scegliere il piano " + level + ". Il garage possiede solo il piano terra (0) e " + garage.getLevels() + " piani.");
    }

    public static Slot createSlot(SlotType type, LocalDateTime date, LocalDateTime endDate, Garage garage, int level, User user) {
        if(user.getRole().equals(Role.ADMIN)) {
            Slot slot = new Slot(type, date, endDate, garage, level);
            List<Slot> slots = garage.getSlotList();
            slots.add(slot);
            garage.setSlotList(slots);
            return slot;
        }
        throw new RuntimeException("Solo un Admin puo' creare un Garage.");
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", type=" + type +
                ", full=" + full +
                ", date=" + date +
                ", endDate=" + endDate +
                ", garage=" + garage +
                ", level=" + level +
                '}';
    }
}
