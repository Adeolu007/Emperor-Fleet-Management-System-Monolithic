package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusProductResponse {
    private String type;
    private String reference;
}
