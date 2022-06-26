package com.example.parkinglotreservation.exception.exception_classes;

public class ResidentNotFoundException extends RuntimeException{

    public ResidentNotFoundException(String param) {
        super("There is no resident with this param " + param);
    }
}
