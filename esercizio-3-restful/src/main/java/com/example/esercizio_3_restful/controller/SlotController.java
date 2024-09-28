package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Garage;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Enum.SlotType;
import com.example.esercizio_3_restful.dto.SlotDTO;
import com.example.esercizio_3_restful.exception.GarageNotFoundException;
import com.example.esercizio_3_restful.exception.InvalidSlotTypeException;
import com.example.esercizio_3_restful.exception.SlotNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.GarageService;
import com.example.esercizio_3_restful.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Autowired
    private GarageService garageService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Slot> createSlot(@RequestBody SlotDTO slotDTO) throws UserNotFoundException, GarageNotFoundException {
        Slot createdSlot = slotService.createSlot(slotDTO);
        return ResponseEntity.ok(createdSlot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slot> getSlotById(@PathVariable Long id) {
        try {
            Slot slot = slotService.getSlotById(id);
            return ResponseEntity.ok(slot);
        } catch (SlotNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @GetMapping
    public ResponseEntity<Page<Slot>> getAllSlots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Slot> slot = slotService.getAllSlots(page, sortBy);
        return ResponseEntity.ok(slot);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Slot> updateSlot(@PathVariable Long id, @RequestBody SlotDTO slotDTO) {
        try {
            Slot updatedSlot = slotService.updateSlot(id, slotDTO);
            return ResponseEntity.ok(updatedSlot);
        } catch (SlotNotFoundException | UserNotFoundException | GarageNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        try {
            String result = slotService.deleteSlot(id);
            return ResponseEntity.ok(result);
        } catch (SlotNotFoundException e) {
            return ResponseEntity.status(404).body("Slot not found with id: " + id);
        }
    }

    @GetMapping("/garage/{garageId}/type/{type}")
    public ResponseEntity<List<Slot>> getSlotsByGarageAndType(@PathVariable Long garageId, @PathVariable SlotType type) throws GarageNotFoundException, InvalidSlotTypeException {

        Garage garage = garageService.getGarageById(garageId);

        List<Slot> slots = slotService.getSlotsByGarageAndType(garage, type);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/garage/{garageId}/level/{level}")
    public ResponseEntity<List<Slot>> getSlotByGarageAndLevel(@PathVariable Long garageId, @PathVariable int level) {
        try {
            Garage garage = garageService.getGarageById(garageId);
            List<Slot> slots = slotService.getSlotByGarageAndLevel(garage, level);
            return ResponseEntity.ok(slots);
        } catch (GarageNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (SlotNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}