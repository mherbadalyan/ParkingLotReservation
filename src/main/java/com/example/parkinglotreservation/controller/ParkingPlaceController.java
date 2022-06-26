package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.service.ParkingPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-lot-reservation/parkingPlace")
@AllArgsConstructor
public class ParkingPlaceController {

    private final ParkingPlaceService parkingPlaceService;

    @PostMapping("/{quantity}")
    private ResponseEntity<?> addParkingPlace(@PathVariable("quantity") Integer quantity) {

        parkingPlaceService.addParkingPlace(quantity);

        return new ResponseEntity<>("Parking places successfully added",
                HttpStatus.OK);
    }

    @DeleteMapping("/{quantity}")
    private ResponseEntity<?> removeParkingPlace(@PathVariable("quantity") Integer quantity) {

        parkingPlaceService.removeParkingPlace(quantity);

        return new ResponseEntity<>("Parking lots successfully added",
                HttpStatus.OK);
    }
}
