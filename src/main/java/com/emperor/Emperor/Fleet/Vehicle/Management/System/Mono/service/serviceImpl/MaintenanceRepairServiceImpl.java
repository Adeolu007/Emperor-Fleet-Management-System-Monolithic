package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.serviceImpl;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceTaskResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateMaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.MaintenanceRepair;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.RepairRecordNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.MaintenanceRepairRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.MaintenanceRepairService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaintenanceRepairServiceImpl implements MaintenanceRepairService {
    private final MaintenanceRepairRepository maintenanceRepairRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceRepairServiceImpl(MaintenanceRepairRepository maintenanceRepairRepository, VehicleRepository vehicleRepository) {
        this.maintenanceRepairRepository = maintenanceRepairRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> createMaintenanceTask(MaintenanceRepairRequest maintenanceRepairRequest) {
        if(!vehicleRepository.existsByLicensePlate(maintenanceRepairRequest.getVehicleLicensePlate())){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.VEHICLE_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.VEHICLE_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this vehicle does not exist")
                    .build());
         //   throw new VehicleNotFoundException("This vehicle does not exist");
        }

        MaintenanceRepair maintenanceRepair = new MaintenanceRepair();
        maintenanceRepair.setCost(maintenanceRepairRequest.getCost());
        maintenanceRepair.setDescription(maintenanceRepairRequest.getDescription());
        maintenanceRepair.setScheduledDate(maintenanceRepairRequest.getScheduledDate());
        maintenanceRepair.setVehicle(vehicleRepository.findByLicensePlate(maintenanceRepairRequest.getVehicleLicensePlate()));
        maintenanceRepairRepository.save(maintenanceRepair);

        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.MAINTENANCE_REPAIR_RECORD_CREATION_CODE)
                .responseMessage(ResponseUtils.MAINTENANCE_REPAIR_RECORD_CREATION_MESSAGE)
                .responseBody(MaintenanceTaskResponseDto.builder().cost(maintenanceRepair.getCost()).vehicleLicensePlate(maintenanceRepair.getVehicle().getLicensePlate())
                        .description(maintenanceRepair.getDescription()).scheduledDate(maintenanceRepair.getScheduledDate()).build())
                .build());  }


    //this endpoint is not working
    @Override
    public ResponseEntity<List<MaintenanceTaskResponseDto>> getAllRepairRecords() {
        List<MaintenanceRepair> allRepairRecord = maintenanceRepairRepository.findAll();
        if (allRepairRecord.isEmpty()) {
            throw new RepairRecordNotFoundException("No repair tasks found");
        }
        List<MaintenanceTaskResponseDto> allRepairRecordDto = allRepairRecord.stream()
                .map(this::convertToMaintenanceTaskResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allRepairRecordDto);
    }

    @Override
    public ResponseEntity<List<MaintenanceTaskResponseDto>> getRepairRecordsByLicensePlate(String licensePlate) {
        List<MaintenanceRepair> repairRecords = maintenanceRepairRepository.findAll();
        System.out.println("Total repair records: " + repairRecords.size());
        List<MaintenanceRepair> filteredRecords = maintenanceRepairRepository.findAll().stream()
                .filter(maintenanceRepair -> maintenanceRepair.getVehicle().getLicensePlate().equals(licensePlate))
                .collect(Collectors.toList());
        System.out.println("Total filteredRecords records: " + filteredRecords.size());
        if (filteredRecords.isEmpty()) {
            System.out.println("No repair records found for license plate: " + licensePlate);
            throw new RepairRecordNotFoundException("No repair records found for license plate: " + licensePlate);
        }

        List<MaintenanceTaskResponseDto> responseDtoList = filteredRecords.stream()
                .map(this::convertToMaintenanceTaskResponseDto)
                .collect(Collectors.toList());
        System.out.println("Response DTO list size: " + responseDtoList.size());

        return ResponseEntity.ok(responseDtoList);
    }

    @Override
    public ResponseEntity<ResponseDto> updateRepairRecord(UpdateMaintenanceRepairRequest updateMaintenanceRepairRequest) {
        Optional<MaintenanceRepair> existingRecord = maintenanceRepairRepository.findById(updateMaintenanceRepairRequest.getId());

        if (existingRecord.isPresent()) {
            MaintenanceRepair maintenanceRepair = existingRecord.get();
            maintenanceRepair.setDescription(updateMaintenanceRepairRequest.getDescription());
            maintenanceRepair.setScheduledDate(updateMaintenanceRepairRequest.getScheduledDate());
            maintenanceRepair.setCost(updateMaintenanceRepairRequest.getCost());
            maintenanceRepairRepository.save(maintenanceRepair);
            return ResponseEntity.ok(ResponseDto.builder()
                    .responseCode(ResponseUtils.MAINTENANCE_REPAIR_RECORD_UPDATED_CODE)
                    .responseMessage(ResponseUtils.MAINTENANCE_REPAIR_RECORD_UPDATED_MESSAGE)
                    .responseBody(MaintenanceTaskResponseDto.builder().cost(maintenanceRepair.getCost()).vehicleLicensePlate(maintenanceRepair.getVehicle().getLicensePlate())
                            .description(maintenanceRepair.getDescription()).scheduledDate(maintenanceRepair.getScheduledDate()).build())
                    .build());
        } else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry, this record does not exist")
                    .build());
        }
    }

//    @Override
//    public ResponseEntity<ResponseDto> deleteRepairRecord(long id) {
//        if (vehicleRepository.existsById(id)) {
//           maintenanceRepairRepository.deleteById(id);
//            return ResponseEntity.ok(ResponseDto.builder()
//                    .responseCode(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DELETED_CODE)
//                    .responseMessage(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DELETED_MESSAGE)
//                    .responseBody("Repair record record has been deleted")
//                    .build());
//        } else {
//            return ResponseEntity.badRequest().body(ResponseDto.builder()
//                    .responseCode(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_CODE)
//                    .responseMessage(ResponseUtils.MAINTENANCE_REPAIR_RECORD_DOES_NOT_EXIST_MESSAGE)
//                    .responseBody("Sorry, Repair record not found for this vehicle")
//                    .build());
//            // throw new RepairRecordNotFoundException("Repair record not found for this vehicle ");
//        }



    private MaintenanceTaskResponseDto convertToMaintenanceTaskResponseDto(MaintenanceRepair repairRecord) {
        return MaintenanceTaskResponseDto.builder().vehicleLicensePlate(repairRecord.getVehicle().getLicensePlate())
                .description(repairRecord.getDescription()).scheduledDate(repairRecord.getScheduledDate())
                        .cost(repairRecord.getCost()).build();
    }
}
