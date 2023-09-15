package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.serviceImpl;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.*;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.AdminRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.TokenRepository;
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
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
//    @Override
//    public ResponseEntity<ResponseDto> signUp(AdminSignUpRequest request) {
//        String regexPattern = "^(.+)@(\\S+)$";
//        if ((request.getEmail().isEmpty())){
//            return ResponseEntity.badRequest().body(ResponseDto.builder()
//                    .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
//                    .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
//                    .build());
//        }
//        Boolean isExists = adminRepository.existsByEmail(request.getEmail());
//        if (isExists){
//            return ResponseEntity.badRequest().body(ResponseDto.builder()
//                    .responseCode(ResponseUtils.EMAIL_EXISTS_CODE)
//                    .responseMessage(ResponseUtils.EMAIL_EXISTS_MESSAGE)
//                    .build());
//        }
//        Admin admin = Admin.builder()
//                .email(request.getEmail())
//                .username(request.getUserName())
//                .status(Status.ACTIVE)
//                .build();
//
//        RoleEntity role = roleRepository.findByRolename("ROLE_ADMIN").get();
//        admin.setRoles(Collections.singleton(role));
////        admin.setRoleame(Collections.singleton(role));
//        adminRepository.save(admin);
//
//        Token token = new Token(admin);
//        log.info("confirmation token: "+ token);
//
//        tokenRepository.save(token);
//
//        EmailDetails message = EmailDetails.builder()
//                .subject("Account Created Successfully")
//                .recipient(admin.getEmail())
//                .messageBody("Account Created Successfully "+"To confirm your email address, please click here :+" +
//                        //change this url ASAP!!!!!!!!!!!!!!
//                        "http://localhost:8080/api/identity/organizer/confirmtoken?token="+token.getToken())
//                .build();
//        emailService.sendSimpleMail(message);
////        sendMail(messages);
//        return ResponseEntity.ok(ResponseDto.builder()
//                .responseCode(ResponseUtils.ADMIN_ACCOUNT_CREATION_SUCCESS_CODE)
//                .responseMessage(ResponseUtils.ADMIN_ACCOUNT_CREATION_SUCCESS_MESSAGE)
//                .responseBody("Your details is under review and your status will " +
//                        "be confirmed in less than 24 Hours")
//                .build());
//    }
//    public ResponseEntity<ResponseDto> signUp(AdminSignUpRequest request) {
//        try {
//            // Log that the method has been called
//            log.info("signUp method called with request: " + request);
//
//            String regexPattern = "^(.+)@(\\S+)$";
//            if (patternMatches(request.getEmail(), regexPattern)) {
//                // Log that the email doesn't match the pattern
//                log.warn("Email does not match the pattern: " + request.getEmail());
//
//                return ResponseEntity.badRequest().body(ResponseDto.builder()
//                        .responseCode(ResponseUtils.EMAIL_IS_NOT_VALID_CODE)
//                        .responseMessage(ResponseUtils.EMAIL_IS_NOT_VALID_MESSAGE)
//                        .build());
//            }
//
//            Boolean isExists = adminRepository.existsByEmail(request.getEmail());
//            if (isExists) {
//                // Log that the email already exists
//                log.warn("Email already exists: " + request.getEmail());
//
//                return ResponseEntity.badRequest().body(ResponseDto.builder()
//                        .responseCode(ResponseUtils.EMAIL_EXISTS_CODE)
//                        .responseMessage(ResponseUtils.EMAIL_EXISTS_MESSAGE)
//                        .build());
//            }
//
//            Admin admin = Admin.builder()
//                    .email(request.getEmail())
//                    .username(request.getUsername())
//                    .status(Status.ACTIVE)
//                    .build();
//
//            RoleEntity role = roleRepository.findByRolename("ROLE_ADMIN").get();
//            admin.setRoles(Collections.singleton(role));
//            adminRepository.save(admin);
//
//            Token token = new Token(admin);
//            log.info("confirmation token: " + token);
//
//            tokenRepository.save(token);
//
//            EmailDetails message = EmailDetails.builder()
//                    .subject("Account Created Successfully")
//                    .recipient(admin.getEmail())
//                    .messageBody("Account Created Successfully. To confirm your email address, please click here: " +
//                            "http://localhost:8080/api/identity/organizer/confirmtoken?token=" + token.getToken())
//                    .build();
//            emailService.sendSimpleMail(message);
//
//            // Log that the account creation was successful
//            log.info("Account creation successful for email: " + admin.getEmail());
//
//            return ResponseEntity.ok(ResponseDto.builder()
//                    .responseCode(ResponseUtils.ADMIN_ACCOUNT_CREATION_SUCCESS_CODE)
//                    .responseMessage(ResponseUtils.ADMIN_ACCOUNT_CREATION_SUCCESS_MESSAGE)
//                    .responseBody("Your details are under review, and your status will be confirmed in less than 24 Hours")
//                    .build());
//        } catch (Exception e) {
//            // Log any exceptions that occur during the signup process
//            log.error("Error occurred during signup: " + e.getMessage(), e);
//
//            // Handle the exception and return an appropriate response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ResponseDto.builder()
//                          //  .responseCode(ResponseUtils.INTERNAL_SERVER_ERROR_CODE)
//                            //.responseMessage(ResponseUtils.INTERNAL_SERVER_ERROR_MESSAGE)
//                            .responseBody("An error occurred during account creation")
//                            .build());
//        }
//    }
//    @Override
//    public ResponseEntity<ResponseDto> confirmToken(String token) {
//
////        Token token1 = tokenRepository.findByToken(token);
////        if (token1 !=null){
////            Admin admin = adminRepository.findByEmailIgnoreCase(token1.getAdmin().getEmail());
//////            organizer.setIsEnabled(true);
////            String temporaryPassword = ResponseUtils.generateTemporaryPassword();
////            organizer.setPassword(passwordEncoder.encode(temporaryPassword));
////            organizerRepository.save(organizer);
////            EmailDetails message = EmailDetails.builder()
////                    .recipient(organizer.getEmail())
////                    .subject("Email Confirmed Successfully")
////                    .messageBody("Your temporary password is: "+ temporaryPassword +" \n " +
////                            "kindly proceed to change your password immediately ")
////                    .build();
////            emailService.sendSimpleEmail(message);
////            return ResponseEntity.ok(CustomResponse.builder()
////                    .responseCode(ResponseUtils.ACCOUNT_CREATION_SUCCESS_CODE)
////                    .responseMessage(ResponseUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
////                    .responseBody("Email Confirmed Successfully")
////                    .build());
////        }
//        return ResponseEntity.badRequest().body(ResponseDto.builder()
//              //  .responseCode(ResponseUtils.INVALID_TOKEN_CODE)
//                //.responseMessage(ResponseUtils.INVALID_TOKEN_MESSAGE)
//                .responseBody("Your Token is either expired or broken")
//                .build());
//    }



//    @Override
//    public ResponseEntity<ResponseDto> updateCredentials(Long userId, AdminUpdateRequest request) {
//        return null;
//    }

//    @Override
//    public ResponseEntity<?> getSingleUserById(Long userId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<List<AdminResponse>> getAllOrganizer() {
//        return null;
//    }

//    @Override
//    public ResponseEntity<ResponseDto> resetPassword(LoginRequest request) {
//        return null;
//    }

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


}
