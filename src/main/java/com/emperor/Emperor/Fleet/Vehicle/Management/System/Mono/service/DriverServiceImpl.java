package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverRegistrationRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriverNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriversNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.DriverRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status.NOT_ACTIVE;

@Service
public class DriverServiceImpl implements DriverService{
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
    private DriverRepository driverRepository;
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
