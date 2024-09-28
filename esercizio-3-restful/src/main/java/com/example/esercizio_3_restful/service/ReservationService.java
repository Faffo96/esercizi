package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.Reservation;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.Enum.CarType;
import com.example.esercizio_3_restful.dto.ReservationDTO;
import com.example.esercizio_3_restful.exception.*;
import com.example.esercizio_3_restful.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    public Reservation createReservation(ReservationDTO reservationDTO) throws UserNotFoundException, CarNotFoundException, SlotNotFoundException, GplNotAtLvl1Exception, NotSameSlotTypeException, MoreThanHalfSlotsException, InvalidSlotTypeException, SlotAlreadyFullException {
        Slot slot = slotService.getSlotById(reservationDTO.getSlot().getId());

        if (!reservationDTO.getSlot().isFull()) {
            Reservation reservation = new Reservation();

            Car car = carService.getCarById(reservationDTO.getCar().getId());

            checkGplAndLevel(car, slot);
            isSameSlotType(car, slot);
            slotService.checkCategoryHalfFull(slot);

            User user = userService.getUserByEmail(reservationDTO.getUser().getEmail());
            reservation.setUser(user);
            reservation.setCar(car);
            reservation.setSlot(slot);
            reservation.setDate(LocalDateTime.now());
            reservation.setMonthly(reservationDTO.isMonthly());
            if (!slot.isFull()) {
                slot.setFull(true);
                if (reservationDTO.isMonthly()) {
                    reservation.setTotal(reservation.getMonthlyPrice());
                }
                reservationRepository.save(reservation);
            } else throw new SlotAlreadyFullException("Il parcheggio " + slot.getId() + " è già occupato.");




            loggerInfo.info("Reservation with id " + reservation.getId() + " created.");
            return reservation;
        } else {
            throw new MoreThanHalfSlotsException("La metà dei parcheggi di tipo " + reservationDTO.getSlot().getType() + " sono occupati. Impossibile prenotare.");
        }

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

    public Reservation updateReservation(Long id, ReservationDTO reservationDTO) throws ReservationNotFoundException, UserNotFoundException, CarNotFoundException, SlotNotFoundException, GplNotAtLvl1Exception, NotSameSlotTypeException, MoreThanHalfSlotsException, InvalidSlotTypeException, SlotAlreadyFullException {
        Reservation reservation = getReservationById(id);

        Car car = carService.getCarByPlateCode(reservationDTO.getCar().getPlateCode());
        Slot slot = slotService.getSlotById(reservationDTO.getSlot().getId());
        checkGplAndLevel(car, slot);
        isSameSlotType(car, slot);
        slotService.checkCategoryHalfFull(slot);

        User user = userService.getUserByEmail(reservationDTO.getUser().getEmail());
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setSlot(slot);
        reservation.setDate(LocalDateTime.now());
        reservation.setMonthly(reservationDTO.isMonthly());
        if (!slot.isFull()) {
            slot.setFull(true);
            if (reservationDTO.isMonthly()) {
                reservation.setTotal(reservation.getMonthlyPrice());
            }
            reservationRepository.save(reservation);
        } else throw new SlotAlreadyFullException("Il parcheggio " + slot.getId() + " è già occupato.");

        loggerInfo.info("Reservation with id " + id + " updated.");

        return reservation;
    }

    public String deleteReservation(Long id) throws ReservationNotFoundException {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
        loggerInfo.info("Reservation with id " + id + " deleted successfully.");
        return "Reservation with id " + id + " deleted successfully.";
    }

    public List<Reservation> getByCarPlate(String plateCode) {
        return reservationRepository.findByCarPlateCode(plateCode);
    }

    public double calculateHoursTotal(Reservation reservation, double hours) throws CheckoutException {
        if (hours > 8) {
            switch (reservation.getSlot().getType()) {
                case LUXURY -> {
                    return ((8 * reservation.getHourPrice()) + ((hours - 8) * (reservation.getHourPrice() + 1)));
                }
                case BIG -> {
                    return ((8 * reservation.getHourPrice()) + ((hours - 8) * (reservation.getHourPrice() + 0.7)));
                }
                case NORMAL -> {
                    return ((8 * reservation.getHourPrice()) + ((hours - 8) * (reservation.getHourPrice() + 0.5)));
                }
                default -> throw new CheckoutException("Errore nel checkout.");
            }
        } else {
            return hours * reservation.getHourPrice();
        }
    }

    public double checkout(Reservation reservation) throws CheckoutException, SlotNotFoundException {
        reservation.setEndDate(LocalDateTime.now());
        Slot slot = slotService.getSlotById(reservation.getSlot().getId());
        slot.setFull(false);
        Duration duration = Duration.between(reservation.getDate(), reservation.getEndDate());
        long hours = duration.toHours();

        double total;

        if (!reservation.isMonthly()) {
            total = calculateHoursTotal(reservation, hours);
        } else {
            total = reservation.getMonthlyPrice();
            reservation.setMonthly(false);
        }

        reservation.setTotal(total);
        reservationRepository.save(reservation);
        return total;
    }

    public void isSameSlotType(Car car, Slot slot) throws NotSameSlotTypeException {
        if (car.getSlotType() != slot.getType()) {
            throw new NotSameSlotTypeException("Un'auto " + car.getSlotType() + " non può essere inserita in un parcheggio per veicoli " + slot.getType() + " .");
        }
    }

    private static void checkGplAndLevel(Car car, Slot slot) throws GplNotAtLvl1Exception {
        if (car.getType() == CarType.GPL && slot.getLevel() != 0) {
            throw new GplNotAtLvl1Exception("Le auto GPL possono parcheggiare solo al piano 0.");
        }
    }
}
