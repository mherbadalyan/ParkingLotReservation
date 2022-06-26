package com.example.parkinglotreservation.exception.exception_classes;

public class BookingNotFoundException extends RuntimeException{
    public BookingNotFoundException(String message) {
        super(message);
    }
}