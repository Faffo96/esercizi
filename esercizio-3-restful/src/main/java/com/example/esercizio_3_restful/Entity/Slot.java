package com.example.esercizio_3_restful.Entity;

import com.example.esercizio_3_restful.Enum.SlotType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "slots")
public class Slot {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private SlotType type;
    private boolean isFull;
    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;
    private int level;
    @OneToMany(mappedBy = "slot")
    @JsonIgnore
    private List<Reservation> reservationList;
}