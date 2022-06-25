package com.example.parkinglotreservation.repository;

import com.example.parkinglotreservation.model.entity.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {
@Query(nativeQuery = true, value = "SELECT * FROM parking_place prkPlc " +
        "WHERE prkPlc.status = 'AVAILABLE' " +
        "ORDER BY prkPlc.id asc limit 1" )
    Optional<ParkingPlace> findFirstByStatusAvailable();

    @Query(nativeQuery = true, value = "SELECT * FROM parking_place prkPlc " +
            "ORDER BY prkPlc.id desc limit 1" )
    Optional<ParkingPlace> findLastParkingPlace();

    @Query(nativeQuery = true, value = "SELECT * FROM parking_place prkPlc " +
            "ORDER BY prkPlc.id desc limit :limit" )
    List<ParkingPlace> findParkingPlacesForRemove(@Param("limit") int quantity);

}
