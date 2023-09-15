package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ApiResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.RoleService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> addRole(@RequestBody RoleRequest request){
        RoleResponse role = roleService.addRole(request);
        ApiResponse<RoleResponse> ar = new ApiResponse<>(HttpStatus.CREATED);
        ar.setMessage(ResponseUtils.SUCCESS_MESSAGE);
        ar.setData(role);
        return new ResponseEntity<>(ar,ar.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteRole(@RequestParam(name = "id") Long id) {
        roleService.deleteRole(id);
        ApiResponse<Void> ar = new ApiResponse<>(HttpStatus.CREATED);
        ar.setMessage(ResponseUtils.USER_DELETED_MESSAGE);
        ar.setData(null);
        return new ResponseEntity<>(ar,ar.getStatus());
    }

}
