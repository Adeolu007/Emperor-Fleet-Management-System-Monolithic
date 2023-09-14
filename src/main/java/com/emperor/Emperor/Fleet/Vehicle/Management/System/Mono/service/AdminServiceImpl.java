package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Admin;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Status;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.AdminRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.config.JwtTokenProvider;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final EmailService emailService;
   // private final ConfirmationTokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<ResponseDto> signUp(AdminSignUpRequest request) {
        String regexPattern = "^(.+)@(\\S+)$";
        if (patternMatches(request.getEmail(), regexPattern)){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
                    .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
                    .build());
        }
        Boolean isExists = adminRepository.existsByEmail(request.getEmail());
        if (isExists){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .responseCode(ResponseUtils.EMAIL_EXISTS_CODE)
                    .responseMessage(ResponseUtils.EMAIL_EXISTS_MESSAGE)
                    .build());
        }
        Admin admin = Admin.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .status(Status.ACTIVE)
                .build();

        RoleEntity role = roleRepository.findByRolename("ROLE_ADMIN").get();
        admin.setRoles(Collections.singleton(role));
//        admin.setRoleame(Collections.singleton(role));
        adminRepository.save(admin);

        ConfirmationToken confirmationToken = new ConfirmationToken(organizer);
        log.info("confirmation token: "+ confirmationToken);

        tokenRepository.save(confirmationToken);

        EmailDetails messages = EmailDetails.builder()
                .subject("Account Created Successfully")
                .recipient(organizer.getEmail())
                .messageBody("Account Created Successfully "+"To confirm your email address, please click here :+" +
                        "http://localhost:8080/api/identity/organizer/confirmtoken?token="+confirmationToken.getToken())
                .build();
        emailService.sendSimpleEmail(messages);
//        sendMail(messages);
        return ResponseEntity.ok(CustomResponse.builder()
                .responseCode(ResponseUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(ResponseUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .responseBody("Your details is under review and your status will " +
                        "be confirmed in less than 24 Hours")
                .build());
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


    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
