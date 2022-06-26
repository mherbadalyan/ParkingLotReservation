package com.example.parkinglotreservation.service;

import com.example.parkinglotreservation.exception.exception_classes.ResidentNotFoundException;
import com.example.parkinglotreservation.model.dto.ResidentDto;
import com.example.parkinglotreservation.model.dto.UpdateResidentDto;
import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.model.mapper.ResidentMapper;
import com.example.parkinglotreservation.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;

    private PasswordEncoder passwordEncoder;

    public ResidentDto getResident(String phone) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            throw new ResidentNotFoundException(phone);
        }

        return residentMapper.convertToDto(residentFromData.get());
    }

    @Transactional
    public  ResidentDto updateResident(String phone, UpdateResidentDto residentDto) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            throw new ResidentNotFoundException(phone);
        }

        residentFromData.get().setPhone(residentDto.getPhone());
        residentFromData.get().setPassword(passwordEncoder.encode(residentDto.getPassword()));

        Resident updatedResident = residentRepository.save(residentFromData.get());

        return residentMapper.convertToDto(updatedResident);
    }
    @Transactional
    public ResidentDto updateResidentAccount(String phone, Double cash) {
        if (cash <= 0) {
            throw new IllegalArgumentException("Incorrect amount of money");
        }

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            throw new ResidentNotFoundException(phone);
        }

        Double newDebt = residentFromData.get().getDebt() - cash;

        residentFromData.get().setDebt(newDebt);

        Resident updatedResident = residentRepository.save(residentFromData.get());

        return residentMapper.convertToDto(updatedResident);
    }
    @Transactional
    public ResidentDto removeResident(String phone) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
           throw new ResidentNotFoundException(phone);
        }

        ResidentDto residentDto = residentMapper.convertToDto(residentFromData.get());

        residentRepository.delete(residentFromData.get());

        return residentDto;
    }
}
