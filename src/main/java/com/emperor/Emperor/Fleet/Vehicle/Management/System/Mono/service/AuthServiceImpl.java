//package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;
//
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AuthResponse;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisterDto;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.config.JwtTokenProvider;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Collections;
//
//@Service
//@AllArgsConstructor
//public class AuthServiceImpl implements AuthService {
//    private DriverRepository driverRepository;
//    private RoleRepository roleRepository;
//    private AuthenticationManager authenticationManager;
//    private JwtTokenProvider jwtTokenProvider;
//
//    private PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public AuthResponse login(LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return AuthResponse.builder()
//                .token(jwtTokenProvider.generateToken(authentication))
//                .build();
//
//    }
//
//    @Override
//    public String register(RegisterDto registerDto) {
//        if (driverRepository.existByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
//            return "Username or Email is already taken";
//        }
//
//        RoleEntity role = roleRepository.findByRolename("ROLE_DRIVER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        DriverEntity user = DriverEntity.builder()
//                .firstName(registerDto.getFirstName())
//                .lastName(registerDto.getLastName())
//                .otherName(registerDto.getOtherName())
//                .username(registerDto.getUsername())
//                .email(registerDto.getEmail())
//                .licenseNumber(ResponseUtils.generateLicenceNumber(ResponseUtils.lengthOfLicenceNumber))
//                .password(passwordEncoder.encode(registerDto.getPassword()))
//                .status(Status.ACTIVE)
//                .roles(Collections.singleton(role))
//                .build();
//
//        driverRepository.save(user);
//
//        return "User registered successfully";
//    }
//}
