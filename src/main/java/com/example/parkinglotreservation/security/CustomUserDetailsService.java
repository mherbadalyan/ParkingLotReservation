package com.example.parkinglotreservation.security;

import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.model.entity.Role;
import com.example.parkinglotreservation.repository.ResidentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private ResidentRepository residentRepository;

    public CustomUserDetailsService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Resident resident = residentRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Resident not found by phone:" + phone));
        return new org.springframework.security.core.userdetails.User(resident.getName(),
                resident.getPassword(), mapRolesToAuthorities(resident.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
