package com.example.parkinglotreservation.exception.exception_classes;

public class ParkingLotsNotAddedException extends RuntimeException{
    public ParkingLotsNotAddedException() {
        super("No parking lots have been added");
    }
}
