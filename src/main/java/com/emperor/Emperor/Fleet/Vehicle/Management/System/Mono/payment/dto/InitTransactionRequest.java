package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto;


import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitTransactionRequest {
    private float amount;
    private String customerName;
    private String customerEmail;
    private String paymentDescription;
}
