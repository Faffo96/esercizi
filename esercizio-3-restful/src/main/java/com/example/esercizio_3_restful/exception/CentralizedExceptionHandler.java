package com.example.esercizio_3_restful.exception;

import com.example.esercizio_3_restful.model.Error;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> UnauthorizedHandler(UnauthorizedException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        error.setErrorCode("UNAUTHORIZED");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundHandler(UserNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("USER_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Object> EmailAlreadyInUseHandler(EmailAlreadyInUseException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_ACCEPTABLE);
        error.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        error.setErrorCode("EMAIL_ALREADY_IN_USE");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> CarNotFoundHandler(CarNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("CAR_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GarageNotFoundException.class)
    public ResponseEntity<Object> GarageNotFoundHandler(GarageNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("GARAGE_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Object> ReservationNotFoundHandler(ReservationNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("RESERVATION_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<Object> SlotNotFoundHandler(SlotNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("SLOT_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotSameSlotTypeException.class)
    public ResponseEntity<Error> NotSameSlotTypeHandler(NotSameSlotTypeException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("SLOT_TYPE_MISMATCH");
        error.setDetails(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CheckoutException.class)
    public ResponseEntity<Object> CheckoutHandler(CheckoutException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("CHECKOUT_ERROR");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GplNotAtLvl1Exception.class)
    public ResponseEntity<Object> GplNotAtLvl1Handler(GplNotAtLvl1Exception e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("GPL_NOT_LVL_0");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidGarageLvlException.class)
    public ResponseEntity<Object> InvalidGarageLvlHandler(InvalidGarageLvlException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("INVALID_GARAGE_LVL");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidSlotTypeException.class)
    public ResponseEntity<Object> InvalidSlotTypeHandler(InvalidSlotTypeException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("INVALID_SLOT_TYPE");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MoreThanHalfSlotsException.class)
    public ResponseEntity<Object> MoreThanHalfSlotsHandler(MoreThanHalfSlotsException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setErrorCode("MORE_THAN_HALF_SLOTS");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
