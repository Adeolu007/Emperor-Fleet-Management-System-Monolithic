package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriverNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriversNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status.NOT_ACTIVE;

@Service
public class DriverServiceImpl implements DriverService{
    @Autowired
    private DriverRepository driverRepository;
    @Override
    public ResponseEntity<DriverInfo> getDriverByLicenseNumber(String licenseNumber) {
        if (!driverRepository.existsByLicenseNumber(licenseNumber)) {
            throw new DriverNotFoundException("Driver with license number " + licenseNumber + " not found");
        }

        DriverEntity driverProfile = driverRepository.findByLicenseNumber(licenseNumber);
        DriverInfo driver = DriverInfo.builder()
                .firstName(driverProfile.getFirstName())
                .lastName(driverProfile.getLastName())
                .licenseNumber(driverProfile.getLicenseNumber())
                .email(driverProfile.getEmail())
                .build();

        return new ResponseEntity<>(driver, HttpStatus.FOUND);
    }


    @Override
//    public ResponseEntity<List<DriverInfo>> getAllDrivers() {
//        List<DriverInfo> driverInfos = driverRepository.findAll().stream()
//                .map(driver -> DriverInfo.builder()
//                        .firstName(driver.getFirstName())
//                        .lastName(driver.getLastName())
//                        .licenseNumber(driver.getLicenseNumber())
//                        .email(driver.getEmail())
//                        .build())
//                .collect(Collectors.toList());
//
//        if (driverInfos.isEmpty()) {
//            throw new DriversNotFoundException("No drivers found");
//        }
//
//        return new ResponseEntity<>(driverInfos, HttpStatus.OK);
//    }
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
    public ResponseEntity<DriverInfo> updateDriver(DriverRegistration driverRegistration) {
        DriverEntity driverProfile = driverRepository.findByLicenseNumber(driverRegistration.getLicenseNumber());

        if (driverProfile == null) {
            throw new DriverNotFoundException("Driver with license number " + driverRegistration.getLicenseNumber() + " not found");
        }

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

        return ResponseEntity.ok(DriverInfo.builder()
                .firstName(driverProfile.getFirstName())
                .lastName(driverProfile.getLastName())
                .licenseNumber(driverProfile.getLicenseNumber())
                .email(driverProfile.getEmail())
                .build());
    }
    @Override
    public ResponseEntity<String> deleteDriver(String licenseNumber) {
        DriverEntity driverToDelete = driverRepository.findByLicenseNumber(licenseNumber);
        if (driverToDelete == null || driverToDelete.getStatus()==NOT_ACTIVE) {
            throw new DriverNotFoundException("Driver with license number " + licenseNumber + " not found");
        }

        driverToDelete.setStatus(NOT_ACTIVE);
        driverRepository.save(driverToDelete);
        return ResponseEntity.ok("Driver with license number " + licenseNumber + " has been deleted");
    }
}
