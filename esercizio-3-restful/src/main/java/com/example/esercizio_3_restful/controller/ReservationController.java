package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Entity.Reservation;
import com.example.esercizio_3_restful.dto.ReservationDTO;
import com.example.esercizio_3_restful.exception.*;
import com.example.esercizio_3_restful.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) throws UserNotFoundException, CarNotFoundException, SlotNotFoundException, MoreThanHalfSlotsException, GplNotAtLvl1Exception, NotSameSlotTypeException, InvalidSlotTypeException {
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
        } catch (MoreThanHalfSlotsException e) {
            throw new RuntimeException(e);
        } catch (GplNotAtLvl1Exception e) {
            throw new RuntimeException(e);
        } catch (NotSameSlotTypeException e) {
            throw new RuntimeException(e);
        } catch (InvalidSlotTypeException e) {
            throw new RuntimeException(e);
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

    @GetMapping("/car/{plateCode}")
    public ResponseEntity<List<Reservation>> getReservationsByCarPlate(@PathVariable String plateCode) {
        List<Reservation> reservations = reservationService.getByCarPlate(plateCode);
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Double> checkout(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            double total = reservationService.checkout(reservation);
            return ResponseEntity.ok(total);
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (CheckoutException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (SlotNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}