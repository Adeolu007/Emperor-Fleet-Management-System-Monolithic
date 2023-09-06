package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String password;
}