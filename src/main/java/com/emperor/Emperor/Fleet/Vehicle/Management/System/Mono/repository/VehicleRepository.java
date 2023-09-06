package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select (count(v) > 0) from Vehicle v where v.licensePlate = ?1")
    boolean existsByLicensePlate(String licensePlate);

    @Query("select v from Vehicle v where v.licensePlate = ?1")
    Vehicle findByLicensePlate(String licensePlate);

    @Query("select v from Vehicle v where v.licensePlate = ?1")
    Vehicle deleteByLicensePlate(String licensePlate);





//    @Query("select (count(v) > 0) from Vehicle v where v.licensePlate = ?1")
//    boolean existsByLicensePlate(String licensePlate);
//
//    @Query("select v from Vehicle v where v.licensePlate = ?1")
//    Vehicle findByLicensePlate(String licensePlate);
//
//    Vehicle findVehicleByLicensePlate(String licensePlate);
//
//    @Transactional
//    @Modifying
//    @Query("delete from Vehicle v where v.licensePlate = ?1")
//    int deleteByLicensePlate(String licensePlate);

}
