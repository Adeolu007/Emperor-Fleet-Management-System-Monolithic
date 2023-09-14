package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DriverRegistrationRequest {
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private Date hireDate;
    private String gender;
    private String maritalStatus;
    private String email;
    private String address;
    private String phoneNumber;
    private Date dateOfBirth;
}