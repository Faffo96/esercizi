package com.example.esercizio_3_restful.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Error {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private int statusCode;
    private String errorCode;
    private String details;
}
