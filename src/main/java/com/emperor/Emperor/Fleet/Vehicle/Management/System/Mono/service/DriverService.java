package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverRegistration;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {
    ResponseEntity<DriverInfo> getDriverByLicenseNumber(String licenseNumber);
    ResponseEntity<List<DriverInfo>> getAllDrivers();
    ResponseEntity<DriverInfo> updateDriver(DriverRegistration driverRegistration);
    ResponseEntity<String> deleteDriver(String licenseNumber);

}
