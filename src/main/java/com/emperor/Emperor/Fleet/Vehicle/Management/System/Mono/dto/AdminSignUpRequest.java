package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AdminSignUpRequest {
    private String email;
    private String userName;
}
