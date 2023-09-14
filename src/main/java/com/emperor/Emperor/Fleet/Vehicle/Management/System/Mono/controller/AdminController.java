package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.*;
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
    private final ReservationService reservationService;

    public AdminController(DriverService driverService, VehicleService vehicleService, FuelService fuelService, MaintenanceRepairService maintenanceRepairService, ReservationService reservationService) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.fuelService = fuelService;
        this.maintenanceRepairService = maintenanceRepairService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<DriverInfo>> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    //Delete not working because of foreign key
    @DeleteMapping("/{licenseNumber}")
    public ResponseEntity<ResponseDto> deleteDriver(@PathVariable String licenseNumber) {
        return driverService.deleteDriver(licenseNumber);
    }


    //register vehicle
    @PostMapping("/register-vehicle")
    public ResponseEntity<ResponseDto> registerVehicle(@RequestBody VehicleRegistrationRequest vehicleRegistrationRequest) {
        return vehicleService.registerNewVehicle(vehicleRegistrationRequest);
    }

    //delete Vehicle
    @DeleteMapping("/vehicle/{licensePlate}")
    public ResponseEntity<ResponseDto> deleteVehicle(@PathVariable String licensePlate) {
        return vehicleService.deleteVehicle(licensePlate);
    }

    //Reassign a new driver to vehicle
    @PutMapping("/change-driver/{licensePlate}")
    public ResponseEntity<String> ChangeDriver(@PathVariable String licensePlate, @RequestParam(value = "license") String driverLicence){
        return vehicleService.changeDriver(licensePlate,driverLicence);
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
   // @GetMapping("/by-license-plate/{licensePlate}")
    @GetMapping("/by-license-plate")
    public ResponseEntity<List<MaintenanceTaskResponseDto>> getRepairRecordsByLicensePlate(@RequestParam(value = "licensePlate")String licensePlate) {
        return maintenanceRepairService.getRepairRecordsByLicensePlate(licensePlate);
    }


    //delete repair record
    //not working **********************

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ResponseDto> deleteRepairRecord(@PathVariable long id) {
//        return maintenanceRepairService.deleteRepairRecord(id);
//    }

    //get all reservations
    @GetMapping("/all-reservations")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return reservationService.getAllReservations();
    }

    //get reservation
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ResponseDto> getReservationById(@PathVariable Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }
}
