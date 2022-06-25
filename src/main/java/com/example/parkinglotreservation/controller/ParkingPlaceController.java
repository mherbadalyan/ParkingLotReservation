package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.service.ParkingPlaceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-lot-reservation/parkingPlace")
@AllArgsConstructor
public class ParkingPlaceController {

    private final ParkingPlaceService parkingPlaceService;

    private final Logger logger = LoggerFactory.getLogger(ParkingPlaceController.class);

    @PostMapping("/{quantity}")
    private ResponseEntity<?> addParkingPlace(@PathVariable("quantity") Integer quantity) {
        if (quantity <= 0) {
            logger.warn("Wrong quantity of parking place");
            return new ResponseEntity<>("Wrong quantity of parking place",
                    HttpStatus.BAD_REQUEST);
        }

        int i = parkingPlaceService.addParkingPlace(quantity);
        if (i == 0) {
            logger.warn("There is problem with adding new parking places");
            return new ResponseEntity<>("There is problem with adding new parking places",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Parking places successfully added",
                HttpStatus.OK);
    }

    @DeleteMapping("/{quantity}")
    private ResponseEntity<?> removeParkingPlace(@PathVariable("quantity") Integer quantity) {
        if (quantity <= 0) {
            return new ResponseEntity<>("Wrong quantity of parking place",
                    HttpStatus.BAD_REQUEST);
        }

        parkingPlaceService.removeParkingPlace(quantity);

        return new ResponseEntity<>("Parking places successfully added",
                HttpStatus.OK);
    }
}
