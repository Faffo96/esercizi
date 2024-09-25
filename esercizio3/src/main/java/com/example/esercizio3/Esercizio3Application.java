package com.example.esercizio3;

import com.example.esercizio3.Entity.*;
import com.example.esercizio3.Enum.CarType;
import com.example.esercizio3.Enum.Role;
import com.example.esercizio3.Enum.SlotType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Esercizio3Application {
	private static LocalDateTime now = LocalDateTime.now();

	public static void main(String[] args) {
		SpringApplication.run(Esercizio3Application.class, args);

		User user = User.createUser("Fabio", Role.USER);
		User user1 = User.createUser("Mario", Role.ADMIN);
		System.out.println("1: " + user);
		System.out.println("2: " + user1);

		Garage garage = Garage.createGarage(5, user1);
		System.out.println("3: " + garage);

		Slot slot = Slot.createSlot(SlotType.NORMAL, now, now.plusHours(2), garage, 0, user1);
		Slot slot1 = Slot.createSlot(SlotType.BIG, now, now.plusDays(3), garage, 1, user1);
		Slot slot2 = Slot.createSlot(SlotType.LUXURY, now, now.plusDays(4), garage, 2, user1);
		System.out.println("4: " + slot);
		System.out.println("5: " + slot1);
		System.out.println("6: " + slot2);
		System.out.println("7: " + garage.getSlotList());

		Car car = Car.createCar("AB123CD", CarType.DIESEL, user);
		Car car1 = Car.createCar("EF123GH", CarType.PETROL, user);
		Car car2 = Car.createCar("IL123MN", CarType.GPL, user1);
		System.out.println("8: " + car);
		System.out.println("9: " + car1);
		System.out.println("10: " + car2);
		System.out.println("11: " + user.getCarList());

		Reservation reservation = new Reservation(user, user.getCarList().get(0), slot);
		System.out.println("12: " + reservation);
	}

}
