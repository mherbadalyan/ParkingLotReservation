package com.example.parkinglotreservation.exception.exception_classes;

public class IncorrectQuantityOfParkingPlaceException extends RuntimeException {
    public IncorrectQuantityOfParkingPlaceException() {
        super("Incorrect quantity of parking place");
    }
}
