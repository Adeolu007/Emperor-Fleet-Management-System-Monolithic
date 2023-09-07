package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.vehicle.licensePlate = ?1 and r.date = ?2 and r.startTime = ?3")
    Reservation findByVehicleAndDateAndStartTime(String licensePlate, LocalDate date, LocalTime startTime);
//    @Query("select r from Reservation r where r.vehicle = ?1 and r.date = ?2 and r.startTime = ?3")
//    Reservation findByVehicleAndDateAndStartTime(String vehicle, LocalDate date, LocalTime startTime);

}
