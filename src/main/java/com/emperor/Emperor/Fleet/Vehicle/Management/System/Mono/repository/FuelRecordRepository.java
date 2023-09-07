package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.FuelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {
    @Query("select f from FuelRecord f where f.vehicle.licensePlate = ?1")
    List<FuelRecord> findByLicensePlate(String licensePlate);

}
