package com.example.parkinglotreservation.service;

import com.example.parkinglotreservation.model.entity.ParkingPlace;
import com.example.parkinglotreservation.model.enums.Status;
import com.example.parkinglotreservation.repository.ParkingPlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingPlaceService {

    private final ParkingPlaceRepository parkingPlaceRepository;

    public int addParkingPlace(Integer quantity) {
        int startNum;
        Optional<ParkingPlace> lastParkingPlace = parkingPlaceRepository.findLastParkingPlace();

        if (lastParkingPlace.isEmpty()) {
            startNum = 1;
        } else {
            startNum = lastParkingPlace.get().getPlaceNumber() + 1;
        }

        List<ParkingPlace> newList = new ArrayList<>();
        ParkingPlace parkingPlace ;

        for (int i = startNum; i < quantity + startNum; i++) {
            parkingPlace = new ParkingPlace();
            parkingPlace.setStatus(Status.AVAILABLE);
            parkingPlace.setPlaceNumber(i);
            newList.add(parkingPlace);
        }

        List<ParkingPlace> parkingPlaces = parkingPlaceRepository.saveAll(newList);
        if (parkingPlaces.isEmpty()) {
            return 0;
        }
        return 1;
    }

    public void removeParkingPlace(Integer quantity) {

        List<ParkingPlace> lastParkingPlace = parkingPlaceRepository.findParkingPlacesForRemove(quantity);

        parkingPlaceRepository.deleteAll(lastParkingPlace);
    }
}
