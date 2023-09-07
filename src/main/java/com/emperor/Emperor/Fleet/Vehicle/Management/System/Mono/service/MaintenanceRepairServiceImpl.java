package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceTaskResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateMaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.MaintenanceRepair;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.RepairRecordNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.MaintenanceRepairRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaintenanceRepairServiceImpl implements MaintenanceRepairService{
    private final MaintenanceRepairRepository maintenanceRepairRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceRepairServiceImpl(MaintenanceRepairRepository maintenanceRepairRepository, VehicleRepository vehicleRepository) {
        this.maintenanceRepairRepository = maintenanceRepairRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public ResponseEntity<String> createMaintenanceTask(MaintenanceRepairRequest maintenanceRepairRequest) {
        if(!vehicleRepository.existsByLicensePlate(maintenanceRepairRequest.getVehicleLicensePlate())){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }

        MaintenanceRepair maintenanceRepair = new MaintenanceRepair();
        maintenanceRepair.setCost(maintenanceRepairRequest.getCost());
        maintenanceRepair.setDescription(maintenanceRepairRequest.getDescription());
        maintenanceRepair.setScheduledDate(maintenanceRepairRequest.getScheduledDate());
        maintenanceRepair.setVehicle(vehicleRepository.findByLicensePlate(maintenanceRepairRequest.getVehicleLicensePlate()));
        maintenanceRepairRepository.save(maintenanceRepair);
        return ResponseEntity.ok( "Vehicle with the License Plate Number " + maintenanceRepair.getVehicle().getLicensePlate() + " has been scheduled for repair");
    }


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
        List<MaintenanceRepair> repairRecords = maintenanceRepairRepository.findByVehicleLicensePlate(licensePlate);

        if (repairRecords.isEmpty()) {
            throw new RepairRecordNotFoundException("No repair records found for license plate: " + licensePlate);
        }
     //
        List<MaintenanceRepair> filteredRecords = repairRecords.stream()
                .filter(record -> record.getVehicle().getLicensePlate().equals(licensePlate))
                .collect(Collectors.toList());

//        return ResponseEntity.ok( repairRecords.stream()
//                .map(this::convertToMaintenanceTaskResponseDto)
//                .collect(Collectors.toList()));
        return ResponseEntity.ok(filteredRecords.stream()
                        .map(this::convertToMaintenanceTaskResponseDto).toList());


//        return ResponseEntity.ok(filteredRecords)
    }

    @Override
    public ResponseEntity<String> updateRepairRecord(UpdateMaintenanceRepairRequest updateMaintenanceRepairRequest) {
        Optional<MaintenanceRepair> existingRecord = maintenanceRepairRepository.findById(updateMaintenanceRepairRequest.getId());

        if (existingRecord.isPresent()) {
            MaintenanceRepair maintenanceRepair = existingRecord.get();
            maintenanceRepair.setDescription(updateMaintenanceRepairRequest.getDescription());
            maintenanceRepair.setScheduledDate(updateMaintenanceRepairRequest.getScheduledDate());
            maintenanceRepair.setCost(updateMaintenanceRepairRequest.getCost());
            maintenanceRepairRepository.save(maintenanceRepair);
            return ResponseEntity.ok("Maintenance and repair record updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteRepairRecord(long id) {
        if (vehicleRepository.existsById(id)) {
           maintenanceRepairRepository.deleteById(id);
            return ResponseEntity.ok("This record has been deleted");
        } else {
            throw new RepairRecordNotFoundException("Repair record not found for this vehicle ");
        }
    }


    private MaintenanceTaskResponseDto convertToMaintenanceTaskResponseDto(MaintenanceRepair repairRecord) {
        return MaintenanceTaskResponseDto.builder().vehicleLicensePlate(repairRecord.getVehicle().getLicensePlate())
                .description(repairRecord.getDescription()).scheduledDate(repairRecord.getScheduledDate())
                        .cost(repairRecord.getCost()).build();
    }
}
