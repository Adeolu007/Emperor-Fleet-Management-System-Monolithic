package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.DriverInfo;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.VehicleRegistration;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.DriverService;
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

    @Autowired
    public AdminController(DriverService driverService, VehicleService vehicleService) {
        this.driverService = driverService;
        this.vehicleService=vehicleService;
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
    public ResponseEntity<String> deleteVehicle(@PathVariable Long licensePlate) {
        return vehicleService.deleteVehicle(licensePlate);
    }
}
