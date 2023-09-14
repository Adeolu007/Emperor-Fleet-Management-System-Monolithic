package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;


import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RegisterDto;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }
}
