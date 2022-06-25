package com.example.parkinglotreservation.model.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String name;

    private String phone;

    private String password;

    private String role;
}
