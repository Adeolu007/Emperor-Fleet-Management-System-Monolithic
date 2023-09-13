package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusResponseBody {
    private String transactionReference;
    private String paymentReference;
    private float amountPaid;
    private float totalPayable;
    private String paidOn;
    private String paymentStatus;
    private String paymentDescription;
    private String currency;
    private String paymentMethod;
    private TransactionStatusCustomerResponse customer;
    private TransactionStatusProductResponse product;
}
