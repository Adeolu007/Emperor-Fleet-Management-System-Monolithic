package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleResponse;

public interface RoleService {
    RoleResponse addRole(RoleRequest request);
    void deleteRole(Long id);

}