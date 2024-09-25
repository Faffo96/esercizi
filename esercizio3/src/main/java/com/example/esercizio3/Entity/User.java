package com.example.esercizio3.Entity;

import com.example.esercizio3.Enum.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private static int counter;
    private int id;
    private String name;
    private List<Car> carList = new ArrayList<>();
    private Role role;
    private Reservation[] reservationList;

    private User(String name, Role role) {
        id += counter;
        this.name = name;
        this.role = role;
        counter++;
    }

    public static User createUser(String name, Role role) {
        return new User(name, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
