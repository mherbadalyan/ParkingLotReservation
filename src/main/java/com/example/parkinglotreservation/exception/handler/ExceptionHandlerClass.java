package com.example.parkinglotreservation.exception.handler;

import com.example.parkinglotreservation.exception.exception_classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerClass {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerClass.class);

    @ExceptionHandler({
            ResidentNotFoundException.class,
            BookingNotFoundException.class,
            IncorrectQuantityOfParkingPlaceException.class,
            IllegalArgumentException.class,
            NoAvailableParkingPlaceException.class,
            ParkingLotsNotAddedException.class})
    public ResponseEntity<?> handleAllCustomException(RuntimeException exception) {
        logger.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}