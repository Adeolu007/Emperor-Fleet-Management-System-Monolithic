package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusCustomerResponse {
    private String email;
    private String name;


//            "cardDetails": null,
//            "accountDetails": null,
//            "accountPayments": [],

}
