package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DriverInfo {
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String email;
}
