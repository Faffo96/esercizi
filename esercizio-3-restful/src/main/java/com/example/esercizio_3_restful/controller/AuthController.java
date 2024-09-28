package com.example.esercizio_3_restful.controller;

import com.example.esercizio_3_restful.Enum.Role;
import com.example.esercizio_3_restful.dto.UserDTO;
import com.example.esercizio_3_restful.dto.UserLoginDTO;
import com.example.esercizio_3_restful.exception.EmailAlreadyInUseException;
import com.example.esercizio_3_restful.exception.UnauthorizedException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.AuthService;
import com.example.esercizio_3_restful.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/registerUser")
    public String registerUser(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) throws BadRequestException, EmailAlreadyInUseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }
        try {
            userDTO.setRole(Role.USER);
            userService.createUser(userDTO);
        } catch (EmailAlreadyInUseException e) {
            throw new EmailAlreadyInUseException(e.getMessage());
        }
        return "User with email " + userDTO.getEmail() + " has been created!";
    }

    @PostMapping("/auth/registerAdmin")
    public String registerAdmin(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) throws BadRequestException, EmailAlreadyInUseException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }
        try {
            userDTO.setRole(Role.ADMIN);
            userService.createUser(userDTO);
        } catch (EmailAlreadyInUseException e) {
            throw new EmailAlreadyInUseException(e.getMessage());
        }
        return "User with email " + userDTO.getEmail() + " has been created!";
    }


/*    @PostMapping("/auth/registerOwner")
    public String registerOwner(@RequestBody @Validated OwnerDTO ownerDTO, BindingResult bindingResult) throws BadRequestException, EmailAlreadyInUseException, OwnerAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }
        try {
            ownerService.createOwner();
        } catch (OwnerAlreadyExistsException e) {
            throw new OwnerAlreadyExistsException(e.getMessage());
        } catch (UserNotFoundException e) {
        }
        return "Owner with email " + ownerDTO.getEmail() + " has been created!";
    }*/

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UserLoginDTO userLoginDTO, BindingResult bindingResult) throws BadRequestException, UserNotFoundException, UnauthorizedException, UserNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }
        return authService.authenticateUserAndCreateToken(userLoginDTO);
    }


}
