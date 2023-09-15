package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String userName;
    private String email;
    private String password;
}
