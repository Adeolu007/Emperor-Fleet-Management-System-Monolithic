package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelService {
    ResponseEntity<ResponseDto> addFuelRecord(FuelRecordDto fuelRecordDto);
    ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(String licensePlate);
    double calculateFuelEfficiency(String licensePlate);

}