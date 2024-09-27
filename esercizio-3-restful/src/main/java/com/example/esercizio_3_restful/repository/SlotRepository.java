package com.example.esercizio_3_restful.repository;


import com.example.esercizio_3_restful.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}