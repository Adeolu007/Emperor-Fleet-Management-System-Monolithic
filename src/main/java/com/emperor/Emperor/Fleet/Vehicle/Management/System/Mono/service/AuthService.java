package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;


import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AuthResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisterDto;

public interface AuthService {

    AuthResponse login (LoginRequest loginRequest);

    String register (RegisterDto registerDto);
}
