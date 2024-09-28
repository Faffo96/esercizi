package com.example.esercizio_3_restful.repository;


import com.example.esercizio_3_restful.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.car.plateCode = :plateCode")
    List<Reservation> findByCarPlateCode(String plateCode);
}
