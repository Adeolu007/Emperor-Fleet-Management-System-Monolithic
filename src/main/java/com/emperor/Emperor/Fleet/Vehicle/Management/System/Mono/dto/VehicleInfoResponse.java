package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VehicleInfoResponse {
    private String licensePlate;
    private String description;
    private String fuelCapacity;
    private String make;
}
