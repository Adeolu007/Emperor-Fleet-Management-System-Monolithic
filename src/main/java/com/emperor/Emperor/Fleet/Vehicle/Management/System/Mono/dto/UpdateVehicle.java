package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVehicle {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private Date registrationDate;
    private Date acquisitionDate;
    private String description;
    private String fuelCapacity;

}
