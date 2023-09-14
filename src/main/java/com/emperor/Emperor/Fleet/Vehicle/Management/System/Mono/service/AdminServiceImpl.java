package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AdminResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.AdminUpdateRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.LoginRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final OrganizerRepository organizerRepository;
    private final EmailService emailService;
    private final ConfirmationTokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<ResponseDto> signUp(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> confirmToken(String token) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> login(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> updateCredentials(Long userId, AdminUpdateRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> getSingleUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AdminResponse>> getAllOrganizer() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> resetPassword(LoginRequest request) {
        return null;
    }
}
