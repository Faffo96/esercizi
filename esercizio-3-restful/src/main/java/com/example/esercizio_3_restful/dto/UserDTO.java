package com.example.esercizio_3_restful.dto;

import com.example.esercizio_3_restful.Enum.Role;
import com.example.esercizio_3_restful.Entity.Car;
import com.example.esercizio_3_restful.Entity.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
