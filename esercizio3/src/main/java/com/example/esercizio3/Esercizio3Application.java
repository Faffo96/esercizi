package com.example.esercizio3;

import com.example.esercizio3.Entity.Car;
import com.example.esercizio3.Entity.User;
import com.example.esercizio3.Enum.CarType;
import com.example.esercizio3.Enum.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Esercizio3Application {

	private static Car createCar(String plateCode, CarType carType, User user) {
		Car car = new Car(plateCode, carType, user);
		List<Car> cars = user.getCarList();
		cars.add(car);
		user.setCarList(cars);
		return car;
	}

	private static User createUser(String name, Role role) {
        return new User(name, role);
	}

	public static void main(String[] args) {
		SpringApplication.run(Esercizio3Application.class, args);

		User user = createUser("Fabio", Role.USER);
		User user2 = createUser("Mario", Role.ADMIN);
		System.out.println(user);
		System.out.println(user2);

		Car car = createCar("AB123CD", CarType.DIESEL, user);
		Car car2 = createCar("AB123CE", CarType.PETROL, user);
		//System.out.println(car);
		System.out.println(user.getCarList());
	}

}
