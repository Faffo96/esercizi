package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.CarType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Car {
    private String plateCode;
    private CarType type;
    private List<Reservation> reservationList;
    private User user;

    public Car(String plateCode, CarType type, User user) {
        this.plateCode = plateCode;
        this.type = type;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "plateCode='" + plateCode + '\'' +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
