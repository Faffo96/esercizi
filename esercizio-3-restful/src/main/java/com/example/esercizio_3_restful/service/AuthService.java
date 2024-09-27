package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.dto.UserLoginDTO;
import com.example.esercizio_3_restful.exception.UnauthorizedException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.security.JwtTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String authenticateUserAndCreateToken(UserLoginDTO userLoginDTO) throws UnauthorizedException, UserNotFoundException {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        if (user != null) {
            logger.info("User found: " + user.getEmail());
            logger.info("Provided password: " + userLoginDTO.getPassword());
            logger.info("Encoded password in DB: " + user.getPassword());
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                logger.info("Password matches");
                return jwtTool.createToken(user);
            } else {
                logger.warn("Password does not match");
                throw new UnauthorizedException("Error in authorization, relogin!");
            }
        } else {
            logger.warn("User not found: " + userLoginDTO.getEmail());
            throw new UserNotFoundException("User with email " + userLoginDTO.getEmail() + " not found.");
        }
    }
}
