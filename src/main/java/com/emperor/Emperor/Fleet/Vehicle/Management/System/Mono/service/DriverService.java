package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {


    AuthResponse login (LoginRequest loginRequest);

    String register (RegisterDto registerDto);
    /////////////////////////////////////
    ResponseEntity<ResponseDto> getDriverByLicenseNumber(String licenseNumber);
    ResponseEntity<List<DriverInfo>> getAllDrivers();
    ResponseEntity<ResponseDto> updateDriver(DriverRegistrationRequest driverRegistration);
    ResponseEntity<ResponseDto> deleteDriver(String licenseNumber);

}
