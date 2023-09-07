package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisteredVehicleDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateVehicle;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration);
    ResponseEntity<RegisteredVehicleDto> findByLicensePlate(String licensePlate);
    ResponseEntity<String> deleteVehicle(String licensePlate);
    ResponseEntity<RegisteredVehicleDto> updateVehicle(UpdateVehicle updateVehicle);


}