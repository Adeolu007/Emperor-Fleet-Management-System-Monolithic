package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceTaskResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.UpdateMaintenanceRepairRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MaintenanceRepairService {
    ResponseEntity<ResponseDto> createMaintenanceTask(MaintenanceRepairRequest maintenanceRepairRequest);
    ResponseEntity<List<MaintenanceTaskResponseDto>> getAllRepairRecords();
    ResponseEntity< List<MaintenanceTaskResponseDto>> getRepairRecordsByLicensePlate(String licensePlate);

    ResponseEntity<ResponseDto> updateRepairRecord(UpdateMaintenanceRepairRequest updateMaintenanceRepairRequest);
//    ResponseEntity<ResponseDto> deleteRepairRecord(long id);
}
