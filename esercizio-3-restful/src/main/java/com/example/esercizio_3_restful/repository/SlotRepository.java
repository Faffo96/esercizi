package com.example.esercizio_3_restful.repository;


import com.example.esercizio_3_restful.Entity.Garage;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Enum.SlotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByGarageAndType(Garage garage, SlotType type);
    List<Slot> findByLevel(int level);
    List<Slot> findByGarageAndLevel(Garage garage, int level);

}