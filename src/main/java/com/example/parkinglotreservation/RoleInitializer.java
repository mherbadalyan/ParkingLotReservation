package com.example.parkinglotreservation;

import com.example.parkinglotreservation.model.entity.Role;
import com.example.parkinglotreservation.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
    }
}
