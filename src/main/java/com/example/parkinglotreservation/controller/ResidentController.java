package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.ResidentDto;
import com.example.parkinglotreservation.service.ResidentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("parking-lot-reservation/resident")
@AllArgsConstructor
public class ResidentController {

    private final Logger logger = LoggerFactory.getLogger(ResidentController.class);
    private final ResidentService residentService;

    @GetMapping("/{phone}")
    private ResponseEntity<?> getResident(@PathVariable("phone") String phone) {

        Optional<ResidentDto> opResidentDto = residentService.getResident(phone);

        if (opResidentDto.isEmpty()) {
            logger.warn("There is no resident with phone " + phone);
            return new ResponseEntity<>("There is no resident with this phone",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(opResidentDto.get(),
                HttpStatus.OK);
    }

    @PutMapping("/{phone}")
    private ResponseEntity<?> updateResident(@PathVariable("phone") String phone,
                                             @RequestBody ResidentDto residentDto) {

        Optional<ResidentDto> updatedResident = residentService.updateResident(phone,residentDto);

        if (updatedResident.isEmpty()) {
            logger.warn("There is no resident with phone " + phone);
            return new ResponseEntity<>("There is no resident with this phone",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedResident.get(),
                HttpStatus.OK);
    }

    @PutMapping("/{phone}/{money}")
    private ResponseEntity<?> updateResidentAccount(@PathVariable("phone") String phone,
                                             @PathVariable("money") Double money) {

        Optional<ResidentDto> updatedResident = residentService.updateResidentAccount(phone,money);

        if (updatedResident.isEmpty()) {
            logger.warn("There is no resident with phone " + phone);
            return new ResponseEntity<>("There is no resident with this phone",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedResident.get(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{phone}")
    private ResponseEntity<?> removeResident(@PathVariable("phone") String phone) {

        Optional<ResidentDto> removedResident = residentService.removeResident(phone);

        if (removedResident.isEmpty()) {
            logger.warn("There is no resident with phone " + phone);
            return new ResponseEntity<>("There is no resident with this phone",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Following resident successfully removed \n" + removedResident.get(),
                HttpStatus.OK);
    }
}
