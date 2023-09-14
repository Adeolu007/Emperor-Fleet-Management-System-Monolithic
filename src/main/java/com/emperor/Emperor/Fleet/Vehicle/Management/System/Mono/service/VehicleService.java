package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<ResponseDto> registerNewVehicle(VehicleRegistrationRequest vehicleRegistrationRequest);
    ResponseEntity<ResponseDto> findByLicensePlate(String licensePlate);
    ResponseEntity<ResponseDto> deleteVehicle(String licensePlate);
    ResponseEntity<ResponseDto> updateVehicle(VehicleRegistrationRequest updateVehicle, String licensePlate);
    ResponseEntity<String> changeDriver (String licensePlate, String driverLicence);


}