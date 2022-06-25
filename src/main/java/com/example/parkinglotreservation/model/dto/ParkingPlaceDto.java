package com.example.parkinglotreservation.model.dto;

import com.example.parkinglotreservation.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingPlaceDto {

    private Long id;

    private Integer placeNumber;

    private Status status;
}
