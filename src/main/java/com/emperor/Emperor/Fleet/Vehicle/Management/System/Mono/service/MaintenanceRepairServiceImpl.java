package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.MaintenanceRepairRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.MaintenanceRepair;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.VehicleNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.MaintenanceRepairRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
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
}
