package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.SlotType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Slot {
    private int id;
    private SlotType type;
    private boolean full;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private Garage garage;
    private List<Reservation> reservationList;
    private int level;

    public Slot(int id, SlotType type, boolean full, LocalDateTime date, LocalDateTime endDate, Garage garage, List<Reservation> reservationList, int level) {
        this.id = id;
        this.type = type;
        this.full = full;
        this.date = date;
        this.endDate = endDate;
        this.garage = garage;
        this.reservationList = reservationList;
        if (level <= garage.getLevels()) {
            this.level = level;
        } else throw new RuntimeException("Impossibile scegliere il piano " + level + ". Il garage possiede solo " + garage.getLevels() + " piani.");
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
