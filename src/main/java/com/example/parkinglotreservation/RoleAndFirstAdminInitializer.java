package com.example.parkinglotreservation;

import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.model.entity.Role;
import com.example.parkinglotreservation.repository.ResidentRepository;
import com.example.parkinglotreservation.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class RoleAndFirstAdminInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final ResidentRepository residentRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("RESIDENT").isEmpty()) {
            Role residentRole = new Role();
            residentRole.setName("RESIDENT");
            roleRepository.save(residentRole);
        }

        if (!residentRepository.existsByName("ADMIN")) {
            Role role;
            role = roleRepository.findByName("ADMIN").get();

            Resident resident = new Resident();
            resident.setName("ADMIN");
            resident.setPhone("1111");
            resident.setPassword(passwordEncoder.encode("1111"));
            resident.setRoles(Collections.singleton(role));

            residentRepository.save(resident);
        }
    }
}
