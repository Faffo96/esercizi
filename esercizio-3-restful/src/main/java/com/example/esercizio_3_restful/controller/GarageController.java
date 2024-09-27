package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Garage;
import com.example.esercizio_3_restful.dto.GarageDTO;
import com.example.esercizio_3_restful.exception.GarageNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @PostMapping
    public ResponseEntity<Garage> createGarage(@RequestBody GarageDTO garageDTO) throws UserNotFoundException {
        Garage createdGarage = garageService.createGarage(garageDTO);
        return ResponseEntity.ok(createdGarage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> getGarageById(@PathVariable Long id) {
        try {
            Garage garage = garageService.getGarageById(id);
            return ResponseEntity.ok(garage);
        } catch (GarageNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @GetMapping
    public ResponseEntity<Page<Garage>> getAllGarages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Garage> garage = garageService.getAllGarages(page, sortBy);
        return ResponseEntity.ok(garage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garage> updateGarage(@PathVariable Long id, @RequestBody GarageDTO garageDTO) {
        try {
            Garage updatedGarage = garageService.updateGarage(id, garageDTO);
            return ResponseEntity.ok(updatedGarage);
        } catch (GarageNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGarage(@PathVariable Long id) {
        try {
            String result = garageService.deleteGarage(id);
            return ResponseEntity.ok(result);
        } catch (GarageNotFoundException e) {
            return ResponseEntity.status(404).body("Garage not found with id: " + id);
        }
    }
}