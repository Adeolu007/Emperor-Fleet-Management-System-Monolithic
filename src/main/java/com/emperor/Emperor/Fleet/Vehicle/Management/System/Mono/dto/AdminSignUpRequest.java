package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminSignUpRequest {
    private String email;
    private String Username;
}
