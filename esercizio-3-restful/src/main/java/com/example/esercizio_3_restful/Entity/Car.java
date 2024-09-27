package com.example.esercizio_3_restful.Entity;

import com.example.esercizio_3_restful.Enum.CarType;
import com.example.esercizio_3_restful.Enum.SlotType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    private String plateCode;
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Enumerated(EnumType.STRING)
    private SlotType slotType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "car")
    @JsonIgnore
    private List<Reservation> reservationList;
}