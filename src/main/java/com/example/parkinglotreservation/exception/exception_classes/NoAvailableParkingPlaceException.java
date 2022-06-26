package com.example.parkinglotreservation.exception.exception_classes;

public class NoAvailableParkingPlaceException extends RuntimeException{

    public NoAvailableParkingPlaceException() {
        super("There is no available parking place");
    }
}