package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.controller;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.service.MonnifyServiceImpl;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto.AccessTokenResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto.InitTransactionRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto.InitTransactionResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/monify")
@AllArgsConstructor
public class MonnifyController {
    private MonnifyServiceImpl monnifyService;

    @PostMapping("/initTransaction")
    public ResponseEntity<InitTransactionResponse> initializeTransaction(@RequestBody InitTransactionRequest request) throws URISyntaxException {
        return monnifyService.initializeTransaction(request);
    }
        @GetMapping("/generateAccessToken")
    public AccessTokenResponse generateAccessToken() throws URISyntaxException {
        return monnifyService.generateAccessToken();
    }
}
