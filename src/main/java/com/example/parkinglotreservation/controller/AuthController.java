package com.example.parkinglotreservation.controller;


import com.example.parkinglotreservation.model.dto.LoginDto;
import com.example.parkinglotreservation.model.dto.SignUpDto;
import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.model.entity.Role;
import com.example.parkinglotreservation.repository.ResidentRepository;
import com.example.parkinglotreservation.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private ResidentRepository residentRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto, HttpServletRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getPhone(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Resident successfully signed", HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto, HttpServletRequest request){

        if(residentRepository.existsByPhone(signUpDto.getPhone())){
            logger.warn("There is a resident with phone " + signUpDto.getPhone());
            return new ResponseEntity<>("There is resident with this phone", HttpStatus.BAD_REQUEST);
        }

        if(residentRepository.existsByName(signUpDto.getName())){
            logger.warn("There is a resident with name " + signUpDto.getName());
            return new ResponseEntity<>("There is resident with this name", HttpStatus.BAD_REQUEST);
        }

        Resident resident = new Resident();
        resident.setName(signUpDto.getName());
        resident.setPhone(signUpDto.getPhone());
        resident.setAccount(0D);
        resident.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = null;
        if(signUpDto.getRole().equals("ADMIN")){
            roles = roleRepository.findByName("ADMIN").get();
        }

        if(signUpDto.getRole().equals("RESIDENT")){
            roles = roleRepository.findByName("RESIDENT").get();
        }

        resident.setRoles(Collections.singleton(roles));

        residentRepository.save(resident);
        return new ResponseEntity<>("Resident successfully registered", HttpStatus.OK);
    }
}
