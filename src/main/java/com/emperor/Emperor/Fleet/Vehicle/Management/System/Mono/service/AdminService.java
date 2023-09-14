package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AdminResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AdminUpdateRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<ResponseDto> signUp(LoginRequest request);
    ResponseEntity<ResponseDto> confirmToken(String token);
    ResponseEntity<ResponseDto> login(LoginRequest request);
    ResponseEntity<ResponseDto> updateCredentials(Long userId, AdminUpdateRequest request);
    ResponseEntity<?> getSingleUserById(Long userId);
    ResponseEntity<List<AdminResponse>>  getAllOrganizer();
    ResponseEntity<ResponseDto> resetPassword(LoginRequest request);
}
