package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.service;



import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Admin;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.AdminRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DriverRepository driverRepository;
    private final AdminRepository adminRepository;


    public CustomUserDetailsService(DriverRepository driverRepository, AdminRepository adminRepository){
        this.driverRepository = driverRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (driverRepository.existsByEmail(username)) {
            DriverEntity userCredential = driverRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

            Set<GrantedAuthority> authorities = userCredential.getRoles().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRolename()))
                    .collect(Collectors.toSet());

            return new User(
                    userCredential.getEmail(),
                    userCredential.getPassword(),
                    authorities
            );
        }else if (adminRepository.existsByEmail(username)) {
            Admin admin = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
            Set<GrantedAuthority> authorities = admin.getRoles().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRolename()))
                    .collect(Collectors.toSet());

            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    authorities
            );
        }
//        else if (adminRepository.existsByEmail(username)) {
//            Admin admin = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
//
//            Set<GrantedAuthority> authorities = admin.getRoleName().stream()
//                    .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
//                    .collect(Collectors.toSet());
//
//            return new User(
//                    admin.getEmail(),
//                    admin.getPassword(),
//                    authorities
//            );
//        }
        else {

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


}
