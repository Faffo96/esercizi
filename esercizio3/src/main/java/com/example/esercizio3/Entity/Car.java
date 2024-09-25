package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.CarType;
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
    private User user;

    public Car(String plateCode, CarType type, User user) {
        this.plateCode = plateCode;
        this.type = type;
        this.user = user;
    }

    public static Car createCar(String plateCode, CarType carType, User user) {
        Car car = new Car(plateCode, carType, user);
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
