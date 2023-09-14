package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminUpdateRequest {
    private String lastName;
    private String otherName;
    private String phoneNumber;
    private String address;
    private String password;
}
