package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.CarType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class Reservation {
    private static int counter;
    private int id;
    private User user;
    private Car car;
    private Slot slot;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private static double hourPrice = 3;
    private static double monthlyPrice = 200;
    private boolean monthly;
    private double total;

    private Reservation(User user, Car car, Slot slot, LocalDateTime date, boolean monthly) {
        this.id += counter;
        this.user = user;
        this.car = car;
        this.slot = slot;
        this.date = date;
        this.monthly = monthly;
        counter++;
    }

    public static Reservation createReservation(User user, Car car, Slot slot, LocalDateTime date, boolean monthly) {
        checkGplAndLevel(car, slot);
        isSameSlotType(car, slot);

        checkCategoryHalfFull(slot);

        if (!slot.isFull()) {
            slot.setFull(true);
            return new Reservation(user, car, slot, date, monthly);
        } else throw new RuntimeException("Il parcheggio " + slot.getId() + " è già occupato.");
    }

    private static void checkGplAndLevel(Car car, Slot slot) {
        if (car.getType() == CarType.GPL && slot.getLevel() != 1) {
            throw new RuntimeException("Le auto GPL possono parcheggiare solo al piano 1.");
        }
    }

    private static void isSameSlotType(Car car, Slot slot) {
        if (car.getSlotType() != slot.getType()) {
            throw new RuntimeException("Un'auto " + car.getSlotType() + " non può essere inserita in un parcheggio per veicoli " + slot.getType() + " .");
        }
    }

    private static void checkCategoryHalfFull(Slot slot) {
        List<Slot> slots = Slot.getSlotByType(slot.getGarage(), slot.getType());
        List<Slot> fullSlots = slots.stream().filter(Slot::isFull).toList();
        if (fullSlots.size() > (slots.size()/2)) {
            throw new RuntimeException("Impossibile prenotare. Il numero di auto " + slot.getType() + " supera il 50% della capienza per la sua categoria.");
        }
    }

    public double checkout(double hours) {
        if (!monthly) {
            endDate = LocalDateTime.now();
            System.out.println("Hours: " + hours);
            System.out.println("Slot Type: " + slot.getType()); // Verifica il tipo di slot
            System.out.println("Hour Price: " + hourPrice);
            slot.setFull(false);
            System.out.println("total " + calculateHoursTotal(hours));
            return calculateHoursTotal(hours);
        } else {
            endDate = LocalDateTime.now();
            slot.setFull(false);
            monthly=false;
            return total = monthlyPrice;
        }

    }

    public double calculateHoursTotal(double hours) {
        if (hours > 8) {
            switch (slot.getType()) {
                case LUXURY -> {
                    return ((8 * hourPrice) + ((hours - 8) * (hourPrice + 1)));
                }
                case BIG -> {
                    return ((8 * hourPrice) + ((hours - 8) * (hourPrice + 0.7)));
                }
                case NORMAL -> {
                    return ((8 * hourPrice) + ((hours - 8) * (hourPrice + 0.5)));
                }
                default -> throw new RuntimeException("Errore nel checkout.");
            }
        } else {
            return hours * hourPrice;
        }
    }

    public static Reservation getByCarPlate(List<Reservation> reservations, String carPlate) {
        return reservations.stream()
                .filter(reservation -> reservation.getCar().getPlateCode().equalsIgnoreCase(carPlate))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nessuna prenotazione trovata per la targa " + carPlate));
    }


    public static double getPrice(String type) {
        if (Objects.equals(type, "hour")) {
            return hourPrice;
        } else {
            return monthlyPrice;
        }
    }

}
