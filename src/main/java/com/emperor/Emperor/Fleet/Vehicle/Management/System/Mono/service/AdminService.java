package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
   // ResponseEntity<ResponseDto> signUp(AdminSignUpRequest request);
    ResponseEntity<ResponseDto> confirmToken(String token);
    //ResponseEntity<ResponseDto> login(LoginRequest request);
    ResponseEntity<ResponseDto> updateCredentials(Long userId, AdminUpdateRequest request);
    ResponseEntity<?> getSingleUserById(Long userId);
    ResponseEntity<List<AdminResponse>>  getAllOrganizer();
    ResponseEntity<ResponseDto> resetPassword(LoginRequest request);

    //
    String register (AdminRegisterRequest adminRegisterRequest);
    AuthResponse login (LoginRequest loginRequest);
}
