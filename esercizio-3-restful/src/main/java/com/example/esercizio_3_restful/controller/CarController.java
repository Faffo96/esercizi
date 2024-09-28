package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.dto.CarDTO;
import com.example.esercizio_3_restful.exception.CarNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CarDTO carDTO) throws UserNotFoundException {
        Car createdCar = carService.createCar(carDTO);
        return ResponseEntity.ok(createdCar);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        try {
            Car car = carService.getCarById(id);
            return ResponseEntity.ok(car);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @GetMapping("/plate/{plateCode}")
    public ResponseEntity<Car> getCarByPlateCode(@PathVariable String plateCode) {
        try {
            Car car = carService.getCarByPlateCode(plateCode);
            return ResponseEntity.ok(car);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Car> car = carService.getAllCars(page, sortBy);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        try {
            Car updatedCar = carService.updateCar(id, carDTO);
            return ResponseEntity.ok(updatedCar);
        } catch (CarNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        try {
            String result = carService.deleteCar(id);
            return ResponseEntity.ok(result);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(404).body("Car not found with id: " + id);
        }
    }
}