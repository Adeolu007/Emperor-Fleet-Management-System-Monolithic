package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {
    ResponseEntity<ResponseDto> getDriverByLicenseNumber(String licenseNumber);
    ResponseEntity<List<DriverInfo>> getAllDrivers();
    ResponseEntity<ResponseDto> updateDriver(DriverRegistration driverRegistration);
    ResponseEntity<ResponseDto> deleteDriver(String licenseNumber);

}
