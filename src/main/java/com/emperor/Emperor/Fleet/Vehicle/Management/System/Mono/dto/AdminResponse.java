package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isEnabled;
    private Set<RoleEntity> roleName;
}
