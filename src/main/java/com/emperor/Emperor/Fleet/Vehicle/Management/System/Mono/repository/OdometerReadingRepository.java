package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.OdometerReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OdometerReadingRepository extends JpaRepository<OdometerReading,Long> {
    @Query("select o from OdometerReading o where o.vehicle.licensePlate = ?1")
    List<OdometerReading> findByVehicle_LicensePlate(String licensePlate);

}
