package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.dto.CarDTO;
import com.example.esercizio_3_restful.exception.CarNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    private static final Logger loggerInfo = LoggerFactory.getLogger("loggerInfo");

    public Car createCar(CarDTO carDTO) throws UserNotFoundException {
        Car car = new Car();
        User user = userService.getUserByEmail(carDTO.getUser().getEmail());
        car.setUser(user);
        car.setType(carDTO.getType());
        car.setSlotType(carDTO.getSlotType());
        car.setPlateCode(carDTO.getPlateCode());

        carRepository.save(car);

        loggerInfo.info("Car with id " + car.getId() + " created.");
        return car;
    }

    public Car getCarById(Long id) throws CarNotFoundException {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + id));
    }

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        loggerInfo.info("Retrieved all dining tables.");
        return cars;
    }

    public Page<Car> getAllCars(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<Car> cars = carRepository.findAll(pageable);
        loggerInfo.info("Retrieved dining tables page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return cars;
    }

    public Car updateCar(Long id, CarDTO carDTO) throws CarNotFoundException, UserNotFoundException {
        Car car = getCarById(id);

        User user = userService.getUserByEmail(carDTO.getUser().getEmail());
        car.setUser(user);
        car.setType(carDTO.getType());
        car.setSlotType(carDTO.getSlotType());
        car.setPlateCode(carDTO.getPlateCode());

        carRepository.save(car);
        loggerInfo.info("Car with id " + id + " updated.");

        return car;
    }

    public String deleteCar(Long id) throws CarNotFoundException {
        Car car = getCarById(id);
        carRepository.delete(car);
        loggerInfo.info("Car with id " + id + " deleted successfully.");
        return "Car with id " + id + " deleted successfully.";
    }
}
