package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Reservation;
import com.example.esercizio_3_restful.dto.ReservationDTO;
import com.example.esercizio_3_restful.exception.CarNotFoundException;
import com.example.esercizio_3_restful.exception.ReservationNotFoundException;
import com.example.esercizio_3_restful.exception.SlotNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) throws UserNotFoundException, CarNotFoundException, SlotNotFoundException {
        Reservation createdReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            return ResponseEntity.ok(reservation);
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @GetMapping
    public ResponseEntity<Page<Reservation>> getAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Reservation> reservation = reservationService.getAllReservations(page, sortBy);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservationDTO);
            return ResponseEntity.ok(updatedReservation);
        } catch (ReservationNotFoundException | UserNotFoundException | CarNotFoundException | SlotNotFoundException e) {
            return ResponseEntity.status(404).body(null); // Not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        try {
            String result = reservationService.deleteReservation(id);
            return ResponseEntity.ok(result);
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.status(404).body("Reservation not found with id: " + id);
        }
    }
}