package com.example.esercizio_3_restful.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "garages")
public class Garage {
    @Id
    @GeneratedValue
    private Long id;
    private int levels;
    @OneToMany(mappedBy = "garage")
    @JsonIgnore
    private List<Slot> slotList;

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", levels=" + levels +
                '}';
    }
}