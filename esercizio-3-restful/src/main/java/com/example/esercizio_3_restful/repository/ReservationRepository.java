package com.example.esercizio_3_restful.repository;


import com.example.esercizio_3_restful.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
