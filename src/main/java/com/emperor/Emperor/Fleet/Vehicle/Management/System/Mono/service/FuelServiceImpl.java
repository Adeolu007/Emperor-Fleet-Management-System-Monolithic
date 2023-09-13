package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.FuelRecordResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.FuelRecord;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.OdometerReading;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.FuelRecordsNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.FuelRecordRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.OdometerReadingRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FuelServiceImpl implements FuelService {
    @Autowired
    private FuelRecordRepository fuelRecordRepository;
    @Autowired
    private OdometerReadingRepository odometerReadingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<ResponseDto> addFuelRecord(FuelRecordDto fuelRecordDto) {
        if(!vehicleRepository.existsByLicensePlate(fuelRecordDto.getLicensePlate())){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.VEHICLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.VEHICLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this vehicle does not exist")
                    .build());
//            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        double totalCost = fuelRecordDto.getLitersFilled() * fuelRecordDto.getCostPerLiter();

        FuelRecord fuelRecord = new FuelRecord();
        fuelRecord.setTotalCost(totalCost);
        fuelRecord.setCostPerLiter(fuelRecordDto.getCostPerLiter());
        fuelRecord.setVehicle(vehicleRepository.findByLicensePlate(fuelRecordDto.getLicensePlate()));
        fuelRecord.setRefuelingDate(fuelRecordDto.getRefuelingDate());
        fuelRecord.setLitersFilled(fuelRecordDto.getLitersFilled());
        fuelRecordRepository.save(fuelRecord);

        FuelRecordResponse savedFuelRecord = FuelRecordResponse.builder()
                .refuelingDate(fuelRecord.getRefuelingDate())
                .costPerLiter(fuelRecord.getCostPerLiter())
                .licensePlate(fuelRecord.getVehicle().getLicensePlate())
                .litersFilled(fuelRecord.getLitersFilled())
                .totalCost(totalCost)
                .build();

        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.FUEL_RECORD_CREATION_CODE)
                .responseMessage(ResponseUtils.FUEL_RECORD_CREATION_MESSAGE)
                .responseBody("A new fuel record has successfully been created for vehicle with Licence Plate " + fuelRecord.getVehicle().getLicensePlate())
                .build());

    }

    @Override
    public ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(String licensePlate) {
        List<FuelRecord> fuelRecords = fuelRecordRepository.findByLicensePlate(licensePlate);

        if (fuelRecords.isEmpty()) {
            throw new FuelRecordsNotFoundException("No fuel records found for license plate: " + licensePlate);
        }

        List<FuelRecordResponse> allFuelRecordsByLicensePlate = fuelRecords.stream()
                .map(this::convertToFuelRecordResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allFuelRecordsByLicensePlate);

    }

    private FuelRecordResponse convertToFuelRecordResponse(FuelRecord fuelRecord) {
        return FuelRecordResponse.builder()
                .refuelingDate(fuelRecord.getRefuelingDate())
                .costPerLiter(fuelRecord.getCostPerLiter())
                .licensePlate(fuelRecord.getVehicle().getLicensePlate())
                .litersFilled(fuelRecord.getLitersFilled())
                .totalCost(fuelRecord.getTotalCost())
                .build();
    }

    public double calculateFuelEfficiency(String licensePlate) {
        // Retrieve all fuel records associated with the provided license plate
        List<FuelRecord> fuelRecords = fuelRecordRepository.findByLicensePlate(licensePlate);
        // If no fuel records are found, return 0.0
        if (fuelRecords.isEmpty()) {
            return 0.0;
        }
        // Calculate the total liters of fuel filled across all fuel records
        double totalLitersFilled = fuelRecords.stream().mapToDouble(FuelRecord::getLitersFilled).sum();
        // Calculate the total distance traveled based on odometer readings
        double totalDistanceTraveled = getTotalDistanceTraveled(licensePlate);

        // If total distance traveled is 0, return 0.0 to avoid division by zero
        if (totalDistanceTraveled == 0.0) {
            return 0.0;
        }
        // Calculate and return the fuel efficiency (distance traveled per liter of fuel)
        return totalDistanceTraveled / totalLitersFilled;
    }

    //    private double getTotalDistanceTraveled(String licensePlate) {
//        // Retrieve all odometer readings associated with the provided license plate
//        List<OdometerReading> odometerReadings = odometerReadingRepository.findByLicensePlateOrderByDate(licensePlate);
//
//        // If no odometer readings are found, return 0.0
//        if (odometerReadings.isEmpty()) {
//            return 0.0;
//        }
//
//        // Calculate total distance traveled based on odometer readings
//        double totalDistance = 0.0;
//        OdometerReading previousReading = odometerReadings.get(0);
//
//        // Iterate through the sorted list of odometer readings
//        for (int i = 1; i < odometerReadings.size(); i++) {
//            OdometerReading currentReading = odometerReadings.get(i);
//            // Calculate the difference between consecutive odometer readings to get distance covered
//            totalDistance += currentReading.getDistance() - previousReading.getDistance();
//            previousReading = currentReading;
//        }
//
//        // Return the calculated total distance traveled
//        return totalDistance;
//    }
    private double getTotalDistanceTraveled(String licensePlate) {
        List<OdometerReading> odometerReadings = odometerReadingRepository.findByVehicle_LicensePlate(licensePlate);

        if (odometerReadings.isEmpty()) {
            return 0.0;
        }

        return IntStream.range(1, odometerReadings.size())
                .mapToDouble(i -> odometerReadings.get(i).getDistance() - odometerReadings.get(i - 1).getDistance())
                .sum();
    }
}
