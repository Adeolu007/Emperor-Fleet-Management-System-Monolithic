package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriverNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriversNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.config.JwtTokenProvider;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status.NOT_ACTIVE;

@Service
public class DriverServiceImpl implements DriverService{

    private DriverRepository driverRepository;
   // private DriverRepository driverRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder;

    public DriverServiceImpl(DriverRepository driverRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthResponse.builder()
                .token(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (driverRepository.existByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
            return "Username or Email is already taken";
        }

        RoleEntity role = roleRepository.findByRolename("ROLE_DRIVER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        DriverEntity user = DriverEntity.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .otherName(registerDto.getOtherName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .licenseNumber(ResponseUtils.generateLicenceNumber(ResponseUtils.lengthOfLicenceNumber))
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .status(Status.ACTIVE)
                .roles(Collections.singleton(role))
                .build();

        driverRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public ResponseEntity<ResponseDto> getDriverByLicenseNumber(String licenseNumber) {
        if (!driverRepository.existsByLicenseNumber(licenseNumber)) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.DRIVER_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.DRIVER_DOES_NOT_EXIST_MESSAGE)
                     .responseBody("Sorry this driver does not exist. Please enter a valid driver License Plate")
                    .build());}
        DriverEntity driverProfile = driverRepository.findByLicenseNumber(licenseNumber).orElseThrow();
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.DRIVER_FOUND_CODE)
                .responseMessage(ResponseUtils.DRIVER_FOUND_MESSAGE)
                .responseBody(DriverInfo.builder()
                                .firstName(driverProfile.getFirstName())
                                .lastName(driverProfile.getLastName())
                                .licenseNumber(driverProfile.getLicenseNumber())
                                .email(driverProfile.getEmail()).build())
                .build());
    }


    @Override
    public ResponseEntity<List<DriverInfo>> getAllDrivers()  {
        List<DriverInfo> driverInfos = driverRepository.findAll().stream()
                .filter(driver -> driver.getStatus() == Status.ACTIVE)
                .map(driver -> DriverInfo.builder()
                        .firstName(driver.getFirstName())
                        .lastName(driver.getLastName())
                        .licenseNumber(driver.getLicenseNumber())
                        .email(driver.getEmail())
                        .build())
                .collect(Collectors.toList());

        if (driverInfos.isEmpty()) {
            throw new DriversNotFoundException("No active drivers found");
        }

        return new ResponseEntity<>(driverInfos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> updateDriver(DriverRegistrationRequest driverRegistration) {
        DriverEntity driverProfile = driverRepository.findByLicenseNumber(driverRegistration.getLicenseNumber()).orElseThrow(() -> new DriverNotFoundException("Driver with license number " + driverRegistration.getLicenseNumber() + " not found"));

        if (driverProfile == null) {

            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.DRIVER_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.DRIVER_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry this driver does not exist. Please enter a valid driver License Plate")
                    .build());  }

        driverProfile.setFirstName(driverRegistration.getFirstName());
        driverProfile.setLastName(driverRegistration.getLastName());
        driverProfile.setAddress(driverRegistration.getAddress());
        driverProfile.setGender(driverRegistration.getGender());
        driverProfile.setEmail(driverRegistration.getEmail());
        driverProfile.setHireDate(driverRegistration.getHireDate());
        driverProfile.setDateOfBirth(driverRegistration.getDateOfBirth());
        driverProfile.setMaritalStatus(driverRegistration.getMaritalStatus());
        driverProfile.setPhoneNumber(driverRegistration.getPhoneNumber());
        driverRepository.save(driverProfile);
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.DRIVER_UPDATED_CODE)
                .responseMessage(ResponseUtils.DRIVER_UPDATED_MESSAGE)
                .responseBody(DriverInfo.builder()
                        .firstName(driverProfile.getFirstName())
                        .lastName(driverProfile.getLastName())
                        .licenseNumber(driverProfile.getLicenseNumber())
                        .email(driverProfile.getEmail()).build())
                .build());
    }
    @Override
    public ResponseEntity<ResponseDto> deleteDriver(String licenseNumber) {
        DriverEntity driverToDelete = driverRepository.findByLicenseNumber(licenseNumber).orElseThrow();
        if (driverToDelete == null || driverToDelete.getStatus()==NOT_ACTIVE) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.DRIVER_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.DRIVER_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry this driver does not exist. Please enter a valid driver License Plate")
                    .build());  }
        driverToDelete.setStatus(NOT_ACTIVE);
        driverRepository.save(driverToDelete);
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.DRIVER_DELETED_CODE)
                .responseMessage(ResponseUtils.DRIVER_DELETED_MESSAGE)
                .responseBody("Driver with license number " + driverToDelete.getLicenseNumber()+ " has been deleted")
                .build());
    }
}
