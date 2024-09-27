package com.example.esercizio_3_restful.entity;

import com.example.esercizio_3_restful.Enum.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String name;
    private List<Car> carList;
    private Role role;
    private List<Reservation> reservationList;
}
