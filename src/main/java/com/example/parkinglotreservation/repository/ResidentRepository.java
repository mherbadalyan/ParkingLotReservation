package com.example.parkinglotreservation.repository;

import com.example.parkinglotreservation.model.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident,Long> {

    Optional<Resident> findByName(String name);

    boolean existsByPhone(String phone);

    Optional<Resident> findByPhone(String phone);

    boolean existsByName(String name);
}
