package com.example.parkinglotreservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    private LocalDate reserveDate;

    private String carNumber;

    private Double bookPrice;

    private ParkingPlaceDto parkingPlace;
}
