package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.DriverService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.FuelService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.VehicleService;
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
    @Autowired
    public AdminController(DriverService driverService, VehicleService vehicleService,FuelService fuelService) {
        this.driverService = driverService;
        this.vehicleService=vehicleService;
        this.fuelService = fuelService;
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
}
