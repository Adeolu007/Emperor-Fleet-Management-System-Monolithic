package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;


import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AdminRegisterRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AuthResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisterDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.AdminService;
//import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.AuthService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
  //  private AuthService authService;
    private final AdminService adminService;
    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(driverService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login-driver")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(driverService.login(loginRequest), HttpStatus.OK);
    }


    //////////////////////////////////////////////////ADMIN
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody AdminRegisterRequest adminRegisterRequest) {
        return adminService.register(adminRegisterRequest);
    }

    @PostMapping("/login")
    public AuthResponse loginAdmin(@RequestBody LoginRequest loginRequest) {
        return adminService.login(loginRequest);
    }
}
