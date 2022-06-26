package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.ResidentDto;
import com.example.parkinglotreservation.model.dto.UpdateResidentDto;
import com.example.parkinglotreservation.service.ResidentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-lot-reservation/resident")
@AllArgsConstructor
public class ResidentController {
    private final ResidentService residentService;

    @GetMapping("/{phone}")
    private ResponseEntity<?> getResident(@PathVariable("phone") String phone) {

        ResidentDto opResidentDto = residentService.getResident(phone);

        return new ResponseEntity<>(opResidentDto,
                HttpStatus.OK);
    }

    @PutMapping("/{phone}")
    private ResponseEntity<?> updateResident(@PathVariable("phone") String phone,
                                             @RequestBody UpdateResidentDto residentDto) {

         ResidentDto updatedResident = residentService.updateResident(phone,residentDto);

        return new ResponseEntity<>(updatedResident,
                HttpStatus.OK);
    }

    @PutMapping("/{phone}/{money}")
    private ResponseEntity<?> addCashTResidentDebt(@PathVariable("phone") String phone,
                                             @PathVariable("money") Double cash) {

        ResidentDto updatedResident = residentService.updateResidentAccount(phone,cash);

        return new ResponseEntity<>(updatedResident,
                HttpStatus.OK);
    }

    @DeleteMapping("/{phone}")
    private ResponseEntity<?> removeResident(@PathVariable("phone") String phone) {

        ResidentDto removedResident = residentService.removeResident(phone);

        return new ResponseEntity<>("Following resident successfully removed \n" + removedResident,
                HttpStatus.OK);
    }
}
