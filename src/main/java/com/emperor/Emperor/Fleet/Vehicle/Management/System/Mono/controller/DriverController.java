package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers") // Define your API endpoint
public class DriverController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final FuelService fuelService;
    private final MaintenanceRepairService maintenanceRepairService;

    private final ReservationService reservationService;

    public DriverController(DriverService driverService, VehicleService vehicleService, FuelService fuelService, MaintenanceRepairService maintenanceRepairService, ReservationService reservationService) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.fuelService = fuelService;
        this.maintenanceRepairService = maintenanceRepairService;
        this.reservationService = reservationService;
    }

    @GetMapping("/{licenseNumber}")
    public ResponseEntity<ResponseDto> getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return driverService.getDriverByLicenseNumber(licenseNumber);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateDriver(@RequestBody DriverRegistration driverRegistration) {
        return driverService.updateDriver(driverRegistration);
    }

    @GetMapping("vehicle/{licensePlate}")
    public ResponseEntity<ResponseDto> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    //update vehicle
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateVehicle(@RequestBody UpdateVehicle updateVehicle) {
        return vehicleService.updateVehicle(updateVehicle);
    }

    //add fuel
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addFuelRecord(@RequestBody FuelRecordDto fuelRecordDto) {
        return fuelService.addFuelRecord(fuelRecordDto);
    }

    //book repair
    @PostMapping ("/repair")
    public ResponseEntity<ResponseDto> createMaintenanceTask(@RequestBody MaintenanceRepairRequest maintenanceRepairRequest){
        return maintenanceRepairService.createMaintenanceTask(maintenanceRepairRequest) ;
    }

    //update repair
    @PutMapping ("/update-repair")
    public ResponseEntity<ResponseDto> updateRepairRecord(@RequestBody UpdateMaintenanceRepairRequest updateMaintenanceRepairRequest) {
        return maintenanceRepairService.updateRepairRecord(updateMaintenanceRepairRequest);
    }

    //create reservation
    @PostMapping("/reservation")
    public ResponseEntity<ResponseDto> createReservation(@RequestBody ReservationDto reservationDto){
        return reservationService.createReservation(reservationDto);
    }
}