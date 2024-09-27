package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Enum.Role;
import com.example.esercizio_3_restful.Enum.SlotType;
import com.example.esercizio_3_restful.entity.Car;
import com.example.esercizio_3_restful.entity.Garage;
import com.example.esercizio_3_restful.entity.Reservation;

import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private List<Car> carList;
    private Role role;
    private List<Reservation> reservationList;
}
