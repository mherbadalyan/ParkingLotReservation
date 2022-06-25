package com.example.parkinglotreservation.model.mapper;

import com.example.parkinglotreservation.model.dto.ParkingPlaceDto;
import com.example.parkinglotreservation.model.entity.ParkingPlace;
import org.springframework.stereotype.Component;

@Component
public class ParkingPlaceMapper implements BaseMapper<ParkingPlace, ParkingPlaceDto>{
    @Override
    public ParkingPlace convertToEntity(ParkingPlaceDto dto) {
        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setPlaceNumber(dto.getPlaceNumber());
        parkingPlace.setStatus(dto.getStatus());
        return parkingPlace;
    }

    @Override
    public ParkingPlaceDto convertToDto(ParkingPlace entity) {
        return ParkingPlaceDto.builder().
                id(entity.getId()).
                placeNumber(entity.getPlaceNumber()).
                status(entity.getStatus()).build();
    }
}
