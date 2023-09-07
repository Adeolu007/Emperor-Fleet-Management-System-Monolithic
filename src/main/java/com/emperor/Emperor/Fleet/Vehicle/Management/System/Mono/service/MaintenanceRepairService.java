package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceRepairRequest;
import org.springframework.http.ResponseEntity;

public interface MaintenanceRepairService {
    ResponseEntity<String> createMaintenanceTask(MaintenanceRepairRequest maintenanceRepairRequest);
}
