package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {
    private String make;
    private String model;
    private String licensePlate;
    private String description;
    private String fuelCapacity;

}
