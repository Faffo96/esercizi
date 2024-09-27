package com.example.test.Entity;

import com.example.test.Enum.SlotType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "slots")
public class Slot {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private SlotType type;
    private boolean full;
    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;
    private int level;
}