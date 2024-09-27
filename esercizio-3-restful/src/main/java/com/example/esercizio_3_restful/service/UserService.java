package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.Enum.Role;
import com.example.esercizio_3_restful.dto.UserDTO;
import com.example.esercizio_3_restful.exception.EmailAlreadyInUseException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*@Autowired
    private JavaMailSender javaMailSender;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger loggerWarn = LoggerFactory.getLogger("loggerWarn");
    private static final Logger loggerTrace = LoggerFactory.getLogger("loggerTrace");
    private static final Logger loggerInfo = LoggerFactory.getLogger("loggerInfo");
    private static final Logger loggerError = LoggerFactory.getLogger("loggerError");
    private static final Logger loggerDebug = LoggerFactory.getLogger("loggerDebug");

    public String createUser(UserDTO userDTO) throws EmailAlreadyInUseException {
        if (isEmailInUse(userDTO.getEmail())) {
            throw new EmailAlreadyInUseException("Email " + userDTO.getEmail() + " already in use.");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        userRepository.save(user);
        loggerTrace.trace("Registration email sent to user: " + user.getEmail());
        /*sendRegistrationMail(user);*/
        loggerTrace.trace("User with email " + user.getEmail() + " saved.");
        return "User with email " + user.getEmail() + " saved.";
    }

    private boolean isEmailInUse(String email) {
        try {
            getUserByEmail(email);
            return true;
        } catch (UserNotFoundException e) {
            // Email not found for User, continue checking
        }

        return false;
    }

    public User getLoggedInUser() throws UsernameNotFoundException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public Page<User> getUsers(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<User> users = userRepository.findAll(pageable);
        loggerInfo.info("Retrieved users page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return users;
    }

    public User updateUser(String email, UserDTO userDTO) throws UserNotFoundException {
        User user = getUserByEmail(email);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        loggerInfo.info("User with email " + user.getEmail() + " updated.");
        return user;
    }

    public String deleteUser(String email) throws UserNotFoundException {
        User user = getUserByEmail(email);
        userRepository.delete(user);
        loggerInfo.info("User with email " + email + " deleted successfully.");
        return "User with email " + email + " deleted successfully.";
    }

    /*private void sendRegistrationMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Rest Service Registration");
        message.setText("Congratulations, " + user.getName() + "! Successful registration to this rest service");

        javaMailSender.send(message);
    }*/


}
