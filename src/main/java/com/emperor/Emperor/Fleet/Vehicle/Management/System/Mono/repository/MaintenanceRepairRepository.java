package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.MaintenanceRepair;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MaintenanceRepairRepository extends JpaRepository<MaintenanceRepair,Long> {
    @Query("SELECT mr FROM MaintenanceRepair mr WHERE mr.vehicle.licensePlate = :licensePlate")
    List<MaintenanceRepair> findByVehicleLicensePlate(@Param("licensePlate") String licensePlate);

    @Override
    void deleteById(Long aLong);


//    @Query("select m from MaintenanceRepair m where m.vehicle.licensePlate = ?1")
//    List<MaintenanceRepair> findByVehicle_LicensePlate(String licensePlate);


}
