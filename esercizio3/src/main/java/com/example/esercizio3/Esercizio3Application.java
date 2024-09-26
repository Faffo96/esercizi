package com.example.esercizio3;

import com.example.esercizio3.Entity.*;
import com.example.esercizio3.Enum.CarType;
import com.example.esercizio3.Enum.Role;
import com.example.esercizio3.Enum.SlotType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class Esercizio3Application {
	private static LocalDateTime now = LocalDateTime.now();
	private static List<User> userList = new ArrayList<>();
	private static List<Garage> garageList = new ArrayList<>();
	private static List<Slot> slotList = new ArrayList<>();
	private static List<Car> carList = new ArrayList<>();
	private static List<Reservation> reservationList = new ArrayList<>();

	private static boolean exit = false;
	private static int select;

	private static void runner() {
		Scanner scanner = new Scanner(System.in);

		while (!exit) {
			System.out.println("Seleziona: ");
			System.out.println("1 - Registra Admin");
			System.out.println("2 - Registra Utente");
			System.out.println("3 - Crea Garage");
			System.out.println("4 - Crea Parcheggi");
			System.out.println("5 - Registra Veicolo");
			System.out.println("6 - Prenota parcheggio");
			System.out.println("7 - Ritiro veicolo");

			select = scanner.nextInt();
			scanner.nextLine();
			switch (select) {
				case 1:
					System.out.println("Registra un Admin: ");
					System.out.println("Nome: ");
					String adminName = scanner.nextLine();
					userList.add(User.createUser(adminName, Role.ADMIN));
					System.out.println("Admin registrato con successo.");
					break;
				case 2:
					System.out.println("Registra un Utente: ");
					System.out.println("Nome: ");
					String userName = scanner.nextLine();
					userList.add(User.createUser(userName, Role.USER));
					System.out.println("User registrato con successo.");
					break;
				case 3:
					System.out.println("Registra un Garage: ");
					System.out.println("Quanti piani possiede il garage? ");
					int levels = scanner.nextInt();
					System.out.println("Chi sta creando il garage? ");
					for (int i = 0; i < userList.size(); i++) {
						System.out.println((i + 1) + " - " + userList.get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > userList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Chi sta creando il garage? ");
						for (int i = 0; i < userList.size(); i++) {
							System.out.println((i + 1) + " - " + userList.get(i));
						}
						select = scanner.nextInt();
					}
					Garage garage = Garage.createGarage(levels, userList.get(select - 1));
					garageList.add(garage);
					System.out.println("Garage creato con successo.");
					break;

				case 4:
					System.out.println("Crea Parcheggi: ");
					System.out.println("In quale garage vuoi creare i parcheggi? ");
					for (int i = 0; i < garageList.size(); i++) {
						System.out.println((i + 1) + " - " + garageList.get(i));
					}
					select = scanner.nextInt();
					while (select > garageList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("In quale garage vuoi creare i parcheggi? ");
						for (int i = 0; i < garageList.size(); i++) {
							System.out.println((i + 1) + " - " + garageList.get(i));
						}
						select = scanner.nextInt();
					}
					Garage garage2 = garageList.get(select - 1);
					System.out.println("A che piano vuoi creare dei parcheggi? (da 0 a " + (garage2.getLevels() - 1) + ") ");
					select = scanner.nextInt();
					while (select > garage2.getLevels() - 1 || select < 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("A che piano vuoi creare dei parcheggi? (da 0 a " + (garage2.getLevels() - 1) + ") ");
						select = scanner.nextInt();
					}
					int level = select;
					System.out.println("Che tipo di parcheggi sono? ");
					System.out.println("1 - Normale");
					System.out.println("2 - Dimensioni Notevoli");
					System.out.println("3 - Auto di lusso");
					select = scanner.nextInt();
					while (select < 0 || select > 4) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Che tipo di parcheggi sono? ");
						System.out.println("1 - Normale");
						System.out.println("2 - Dimensioni Notevoli");
						System.out.println("3 - Auto di lusso");
						select = scanner.nextInt();
					}
					SlotType slotType = null;
					switch (select) {
						case 1:
							slotType = SlotType.NORMAL;
							break;
						case 2:
							slotType = SlotType.BIG;
							break;
						case 3:
							slotType = SlotType.LUXURY;
							break;
					}
					;

					System.out.println("Chi sta creando i parcheggi? ");
					for (int i = 0; i < userList.size(); i++) {
						System.out.println((i + 1) + " - " + userList.get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > userList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Chi sta creando i parcheggi? ");
						for (int i = 0; i < userList.size(); i++) {
							System.out.println((i + 1) + " - " + userList.get(i));
						}
						select = scanner.nextInt();
						scanner.nextLine();
					}
					int user2 = select - 1;
					System.out.println("Quanti parcheggi vuoi creare per il piano " + level + " del garage " + garage2.getId() + "?");
					int quantity = scanner.nextInt();
					scanner.nextLine();
					for (int i = 0; i < quantity; i++) {
						slotList.add(Slot.createSlot(slotType, garage2, level, userList.get(user2)));
					}
					System.out.println(quantity + " parcheggi " + slotType + " del garage " + garage2.getId() + " sono stati creati correttamente.");
					break;

				case 5:
					System.out.println("Chi sta registrando il veicolo? ");
					for (int i = 0; i < userList.size(); i++) {
						System.out.println((i + 1) + " - " + userList.get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > userList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Chi sta registrando il veicolo? ");
						for (int i = 0; i < userList.size(); i++) {
							System.out.println((i + 1) + " - " + userList.get(i));
						}
						select = scanner.nextInt();
						scanner.nextLine();
					}
					int user3 = select - 1;
					System.out.println("Targa: ");
					String carPlate = scanner.nextLine();
					System.out.println("Che carburante utilizza?");
					System.out.println("1 - Benzina");
					System.out.println("2 - Diesel");
					System.out.println("3 - GPL");
					select = scanner.nextInt();
					while (select <= 0 || select > 3) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Che carburante utilizza? ");
						System.out.println("1 - Benzina");
						System.out.println("2 - Diesel");
						System.out.println("3 - GPL");
						select = scanner.nextInt();
					}
					CarType carType2 = null;
					switch (select) {
						case 1:
							carType2 = CarType.PETROL;
							break;
						case 2:
							carType2 = CarType.DIESEL;
							break;
						case 3:
							carType2 = CarType.GPL;
							break;
					}
					;

					System.out.println("Che tipo di auto è?");
					System.out.println("1 - Normale");
					System.out.println("2 - Dimensioni Notevoli");
					System.out.println("3 - Auto di lusso");
					select = scanner.nextInt();
					while (select <= 0 || select > 3) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Che tipo di parcheggio è? ");
						System.out.println("1 - Normale");
						System.out.println("2 - Dimensioni Notevoli");
						System.out.println("3 - Auto di lusso");
						select = scanner.nextInt();
					}
					SlotType slotType2 = null;
					switch (select) {
						case 1:
							slotType2 = SlotType.NORMAL;
							break;
						case 2:
							slotType2 = SlotType.BIG;
							break;
						case 3:
							slotType2 = SlotType.LUXURY;
							break;
					}
					;
					carList.add(Car.createCar(carPlate, carType2, slotType2, userList.get(user3)));
					System.out.println("Veicolo con targa " + carPlate + " è stato registrato con successo dal proprietario " + userList.get(user3).getName());
					break;

				case 6:
					System.out.println("Chi sta prenotando un parcheggio? ");
					for (int i = 0; i < userList.size(); i++) {
						System.out.println((i + 1) + " - " + userList.get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > userList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Chi sta registrando il veicolo? ");
						for (int i = 0; i < userList.size(); i++) {
							System.out.println((i + 1) + " - " + userList.get(i));
						}
						select = scanner.nextInt();
						scanner.nextLine();
					}
					User user4 = userList.get(select - 1);
					System.out.println("Quale auto vuoi parcheggiare?");
					for (int i = 0; i < user4.getCarList().size(); i++) {
						System.out.println((i + 1) + " - " + user4.getCarList().get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > user4.getCarList().size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Quale auto vuoi parcheggiare? ");
						for (int i = 0; i < user4.getCarList().size(); i++) {
							System.out.println((i + 1) + " - " + user4.getCarList().get(i));
						}
						select = scanner.nextInt();
						scanner.nextLine();
					}
					Car car = carList.get(select - 1);
					System.out.println("scegli un parcheggio: ");
					for (int i = 0; i < slotList.size(); i++) {
						System.out.println((i + 1) + " - " + slotList.get(i));
					}
					select = scanner.nextInt();
					scanner.nextLine();
					while (select > slotList.size() || select <= 0) {
						System.out.println("Selezione " + (select) + " inesistente. ");
						System.out.println("Scegli un parcheggio: ");
						for (int i = 0; i < slotList.size(); i++) {
							System.out.println((i + 1) + " - " + slotList.get(i));
						}
						select = scanner.nextInt();
						scanner.nextLine();
					}
					Slot slot = slotList.get(select - 1);
					System.out.println(slot);
					System.out.println("Vuoi prenotare per tutto il mese al costo di " + Reservation.getPrice("monthly") + "euro? (true o false)");
					boolean monthly = scanner.nextBoolean();

					reservationList.add(Reservation.createReservation(user4, car, slot, now, monthly));
					System.out.println("Congratulazioni " + user4.getName() + "! Hai riservato un parcheggio per il tuo veicolo con targa " + car.getPlateCode() + " in data " + now + ". Mensile: [" + monthly + "]");
					break;
				case 7:
					System.out.println("Targa: ");
					String carPlate1 = scanner.nextLine();
					Reservation reservation = Reservation.getByCarPlate(reservationList, carPlate1);
					long hours = 0;
					if (!reservation.isMonthly()) {
						System.out.println("Quante ore hai sostato? (Domanda a scopo simulativo, non applicabile a un utilizzo reale. La data di creazione viene salvata al checkin mentre la data di ritiro al checkout.)");
						hours = scanner.nextInt();
						scanner.nextLine();
						System.out.println("Vuoi ritirare il veicolo? Il costo è di " + reservation.calculateHoursTotal(hours) + "euro? (true o false)");
					} else {
						reservation.setEndDate(LocalDateTime.now().plusHours(hours));
						System.out.println("Vuoi ritirare il veicolo? Il costo è di " + Reservation.getPrice("monthly") + "euro? (true o false)");
					}
					boolean yes = scanner.nextBoolean();
					if (yes) {
						System.out.println("Runner" + hours);
						double total = reservation.checkout(hours);
						System.out.println("Il veicolo con targa " + carPlate1 + " è stato ritirato. Hai pagato " + total + "euro.");
					} else {
						System.out.println("Ritiro annullato.");
					}
					break;
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Esercizio3Application.class, args);
		User user = User.createUser("Fabio", Role.ADMIN);
		userList.add(user);
		//System.out.println("1: " + user);
		//System.out.println("2: " + user1);

		Garage garage = Garage.createGarage(5, user);
		garageList.add(garage);
		//System.out.println("3: " + garage);

		Slot slot = Slot.createSlot(SlotType.LUXURY, garage, 0, user);
		Slot slot1 = Slot.createSlot(SlotType.LUXURY, garage, 1, user);
		Slot slot2 = Slot.createSlot(SlotType.LUXURY, garage, 2, user);
		slotList.add(slot);
		slotList.add(slot1);
		slotList.add(slot2);
		//System.out.println("4: " + slot);
		//System.out.println("5: " + slot1);
		//System.out.println("6: " + slot2);
		//System.out.println("7: " + garage.getSlotList());

		Car car = Car.createCar("AB123CD", CarType.DIESEL, SlotType.LUXURY, user);
		Car car1 = Car.createCar("EF123GH", CarType.PETROL, SlotType.BIG, user);
		carList.add(car);
		carList.add(car1);
		//Car car2 = Car.createCar("IL123MN", CarType.GPL, SlotType.NORMAL, user1);
		//System.out.println("8: " + car);
		//System.out.println("9: " + car1);
		//System.out.println("10: " + car2);
		//System.out.println("11: " + user.getCarList());

		//Reservation reservation = Reservation.createReservation(user, user.getCarList().get(0), slot2, now, now.plusHours(0), true);

		//System.out.println("12: " + reservation);

		//System.out.println("13: " + Slot.getSlotByLevel(garage, 0).size());

		//System.out.println("14: " + Slot.getSlotByType(garage, SlotType.LUXURY));

		//System.out.println(reservation.checkout());

		runner();
	}


}
