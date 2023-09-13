package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateVehicle;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<ResponseDto> registerNewVehicle(VehicleRegistration vehicleRegistration);
    ResponseEntity<ResponseDto> findByLicensePlate(String licensePlate);
    ResponseEntity<ResponseDto> deleteVehicle(String licensePlate);
    ResponseEntity<ResponseDto> updateVehicle(UpdateVehicle updateVehicle);
    ResponseEntity<String> changeDriver (String licensePlate, String driverLicence);


}