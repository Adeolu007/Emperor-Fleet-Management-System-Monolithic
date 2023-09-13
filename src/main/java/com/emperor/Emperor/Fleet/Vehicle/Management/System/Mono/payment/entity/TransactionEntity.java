package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionRef;

    @Column(nullable = false, unique = true)
    private String paymentRef;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String checkoutUrl;

}
