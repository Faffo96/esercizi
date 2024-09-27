package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.dto.SlotDTO;
import com.example.esercizio_3_restful.exception.GarageNotFoundException;
import com.example.esercizio_3_restful.exception.SlotNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping
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
    public ResponseEntity<Slot> updateSlot(@PathVariable Long id, @RequestBody SlotDTO slotDTO) {
        try {
            Slot updatedSlot = slotService.updateSlot(id, slotDTO);
            return ResponseEntity.ok(updatedSlot);
        } catch (SlotNotFoundException | UserNotFoundException | GarageNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        try {
            String result = slotService.deleteSlot(id);
            return ResponseEntity.ok(result);
        } catch (SlotNotFoundException e) {
            return ResponseEntity.status(404).body("Slot not found with id: " + id);
        }
    }
}