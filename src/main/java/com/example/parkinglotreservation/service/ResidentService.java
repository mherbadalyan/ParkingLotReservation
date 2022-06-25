package com.example.parkinglotreservation.service;

import com.example.parkinglotreservation.model.mapper.ResidentMapper;
import com.example.parkinglotreservation.model.dto.ResidentDto;
import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;

    private PasswordEncoder passwordEncoder;

    public Optional<ResidentDto> getResident(String phone) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(residentMapper.convertToDto(residentFromData.get()));
    }

    public Optional<ResidentDto> updateResident(String phone, ResidentDto residentDto) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            return Optional.empty();
        }

        residentFromData.get().setPhone(residentDto.getPhone());
        residentFromData.get().setPassword(passwordEncoder.encode(residentDto.getPassword()));

        Resident updatedResident = residentRepository.save(residentFromData.get());

        return Optional.of(residentMapper.convertToDto(updatedResident));
    }

    public Optional<ResidentDto> updateResidentAccount(String phone, Double money) {
        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            return Optional.empty();
        }

        Double newAccount = residentFromData.get().getAccount() - money;

        residentFromData.get().setAccount(newAccount);

        Resident updatedResident = residentRepository.save(residentFromData.get());

        return Optional.of(residentMapper.convertToDto(updatedResident));
    }

    public Optional<ResidentDto> removeResident(String phone) {

        Optional<Resident> residentFromData = residentRepository.findByPhone(phone);

        if (residentFromData.isEmpty()) {
            return Optional.empty();
        }
        ResidentDto residentDto = residentMapper.convertToDto(residentFromData.get());

        residentRepository.delete(residentFromData.get());

        return Optional.of(residentDto);
    }
}
