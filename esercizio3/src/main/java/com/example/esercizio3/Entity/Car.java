package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.CarType;
import com.example.esercizio3.Enum.SlotType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Car {
    private String plateCode;
    private CarType type;
    private SlotType slotType;
    private User user;

    public Car(String plateCode, CarType type, SlotType slotType, User user) {
        this.plateCode = plateCode;
        this.type = type;
        this.slotType = slotType;
        this.user = user;
    }

    public static Car createCar(String plateCode, CarType carType, SlotType slotType, User user) {
        Car car = new Car(plateCode, carType, slotType, user);
        List<Car> cars = user.getCarList();
        cars.add(car);
        user.setCarList(cars);
        return car;
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
