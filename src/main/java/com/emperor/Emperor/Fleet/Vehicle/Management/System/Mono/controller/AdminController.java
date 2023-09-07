package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceTaskResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final FuelService fuelService;
    private final MaintenanceRepairService maintenanceRepairService;

    public AdminController(DriverService driverService, VehicleService vehicleService, FuelService fuelService, MaintenanceRepairService maintenanceRepairService) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.fuelService = fuelService;
        this.maintenanceRepairService = maintenanceRepairService;
    }

    @GetMapping
    public ResponseEntity<List<DriverInfo>> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    //Delete not working because of foreign key
    @DeleteMapping("/{licenseNumber}")
    public ResponseEntity<String> deleteDriver(@PathVariable String licenseNumber) {
        return driverService.deleteDriver(licenseNumber);
    }


    //register vehicle
    @PostMapping("/register-vehicle")
    public ResponseEntity<String> registerVehicle(@RequestBody VehicleRegistration vehicleRegistration) {
        return vehicleService.registerNewVehicle(vehicleRegistration);
    }

    //delete Vehicle
    @DeleteMapping("/vehicle/{licensePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String licensePlate) {
        return vehicleService.deleteVehicle(licensePlate);
    }

    //get fuel records by license plate
    @GetMapping("/records/{licensePlate}")
    public ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(@PathVariable String licensePlate) {
        return fuelService.getFuelRecordsByLicensePlate(licensePlate);
    }

    //fuel Efficacy
    // *** Check the logic again, I'm not too sure about this
    @GetMapping("/efficiency/{licensePlate}")
    public ResponseEntity<Double> calculateFuelEfficiency(@PathVariable String licensePlate) {
        double fuelEfficiency = fuelService.calculateFuelEfficiency(licensePlate);
        return ResponseEntity.ok(fuelEfficiency);
    }

    //get all repair method
    @GetMapping("/all-repair")
    public ResponseEntity<List<MaintenanceTaskResponseDto>> getAllRepairRecords() {
        return maintenanceRepairService.getAllRepairRecords();
    }

    //get all repair by license plate
    //this endpoint is not working *********************************************************
    @GetMapping("/by-license-plate/{licensePlate}")
    public ResponseEntity<List<MaintenanceTaskResponseDto>> getRepairRecordsByLicensePlate(String licensePlate) {
        return maintenanceRepairService.getRepairRecordsByLicensePlate(licensePlate);
    }


    //delete repair record
    //not working **********************

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRepairRecord(@PathVariable long id) {
        return maintenanceRepairService.deleteRepairRecord(id);
    }
}
