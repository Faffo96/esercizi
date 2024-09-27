package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.Reservation;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.dto.ReservationDTO;
import com.example.esercizio_3_restful.exception.CarNotFoundException;
import com.example.esercizio_3_restful.exception.ReservationNotFoundException;
import com.example.esercizio_3_restful.exception.SlotNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private SlotService slotService;

    private static final Logger loggerInfo = LoggerFactory.getLogger("loggerInfo");

    public Reservation createReservation(ReservationDTO reservationDTO) throws UserNotFoundException, CarNotFoundException, SlotNotFoundException {
        Reservation reservation = new Reservation();
        User user = userService.getUserByEmail(reservationDTO.getUser().getEmail());
        reservation.setUser(user);
        Car car = carService.getCarById(reservationDTO.getCar().getId());
        reservation.setCar(car);
        Slot slot = slotService.getSlotById(reservationDTO.getSlot().getId());
        reservation.setDate(LocalDateTime.now());
        reservation.setMonthly(reservationDTO.isMonthly());
        reservation.setTotal(reservationDTO.getTotal());

        reservationRepository.save(reservation);

        loggerInfo.info("Reservation with id " + reservation.getId() + " created.");
        return reservation;
    }

    public Reservation getReservationById(Long id) throws ReservationNotFoundException {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        loggerInfo.info("Retrieved all dining tables.");
        return reservations;
    }

    public Page<Reservation> getAllReservations(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        loggerInfo.info("Retrieved dining tables page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return reservations;
    }

    public Reservation updateReservation(Long id, ReservationDTO reservationDTO) throws ReservationNotFoundException, UserNotFoundException, CarNotFoundException, SlotNotFoundException {
        Reservation reservation = getReservationById(id);

        User user = userService.getUserByEmail(reservationDTO.getUser().getEmail());
        reservation.setUser(user);
        Car car = carService.getCarById(reservationDTO.getCar().getId());
        reservation.setCar(car);
        Slot slot = slotService.getSlotById(reservationDTO.getSlot().getId());
        reservation.setDate(LocalDateTime.now());
        reservation.setMonthly(reservationDTO.isMonthly()); 
        reservation.setTotal(reservationDTO.getTotal());


        reservationRepository.save(reservation);
        loggerInfo.info("Reservation with id " + id + " updated.");

        return reservation;
    }

    public String deleteReservation(Long id) throws ReservationNotFoundException {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
        loggerInfo.info("Reservation with id " + id + " deleted successfully.");
        return "Reservation with id " + id + " deleted successfully.";
    }
}
