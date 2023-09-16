package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.serviceImpl;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.DriverNotFoundException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.AdminRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.config.JwtTokenProvider;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.AdminService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.EmailService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status.NOT_ACTIVE;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(AdminRegisterRequest adminRegisterRequest) {
        log.info(String.format("The name is '%s'", adminRegisterRequest.getUserName()));
        log.info("Password received: " + adminRegisterRequest.getPassword());
        if (adminRepository.existsByEmail(adminRegisterRequest.getEmail())) {
            return "Username or Email is already taken";
        }

        RoleEntity role = roleRepository.findByRolename("ROLE_ADMIN").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        log.info(String.valueOf(adminRegisterRequest.getPassword()));
        log.info(String.valueOf(adminRegisterRequest.getEmail()));
        Admin admin = Admin.builder()
                .firstName(adminRegisterRequest.getFirstName())
                .lastName(adminRegisterRequest.getLastName())
                .otherName(adminRegisterRequest.getOtherName())
                .userName(adminRegisterRequest.getUserName())
                .email(adminRegisterRequest.getEmail())
                .licenseNumber(ResponseUtils.generateLicenceNumber(ResponseUtils.lengthOfLicenceNumber))
                .password(passwordEncoder.encode(adminRegisterRequest.getPassword()))
                .status(Status.ACTIVE)
                .roles(Collections.singleton(role))
                .build();

        adminRepository.save(admin);

        return "Admin registered successfully";
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Welcome to Login Endpoint");
        log.info("Password: " +loginRequest.getPassword()+" Username: "+loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthResponse.builder()
                .token(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAdmin(String licenseNumber) {
        Admin adminToDelete = adminRepository.findByLicenseNumber(licenseNumber).orElseThrow();
        if (adminToDelete == null || adminToDelete.getStatus()==NOT_ACTIVE) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.DRIVER_DOES_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.DRIVER_DOES_NOT_EXIST_MESSAGE)
                    .responseBody("Sorry this admin does not exist. Please enter a valid driver License Plate")
                    .build());  }
        adminToDelete.setStatus(NOT_ACTIVE);
        adminRepository.save(adminToDelete);
        return ResponseEntity.ok(ResponseDto.builder()
                .responseCode(ResponseUtils.DRIVER_DELETED_CODE)
                .responseMessage(ResponseUtils.DRIVER_DELETED_MESSAGE)
                .responseBody("Admin with license number " + adminToDelete.getLicenseNumber()+ " has been deleted")
                .build());
    }

//    @Override
//    public ResponseEntity<ResponseDto> updateCredentials( AdminUpdateRequest request) {
//        Admin admin = adminRepository.findByLicenseNumber(request.getLicenseNumber()).orElseThrow(() -> new DriverNotFoundException("Admin with license number " + request.getLicenseNumber() + " not found"));
//        if (admin == null) {
//            return ResponseEntity.badRequest().body(ResponseDto.builder()
//                    .responseCode(ResponseUtils.DRIVER_DOES_NOT_EXIST_CODE)
//                    .responseMessage(ResponseUtils.DRIVER_DOES_NOT_EXIST_MESSAGE)
//                    .responseBody("Sorry this Admin does not exist. Please enter a valid driver License Plate")
//                    .build());  }
//        admin.setLastName(request.getLastName());
//        admin.setPhoneNumber(request.getPhoneNumber());
//        admin.setOtherName(request.getOtherName());
//        admin.setAddress(request.getAddress());
//        adminRepository.save(admin);
//        return ResponseEntity.ok(ResponseDto.builder()
//                .responseCode(ResponseUtils.DRIVER_UPDATED_CODE)
//                .responseMessage(ResponseUtils.DRIVER_UPDATED_MESSAGE)
//                .responseBody(DriverInfo.builder()
//                        .firstName(admin.getFirstName())
//                        .lastName(admin.getLastName())
//                        .licenseNumber(admin.getLicenseNumber())
//                        .email(admin.getEmail()).build())
//                .build());
//    }


}
