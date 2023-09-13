package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.payment.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
    Optional<TransactionEntity> findByCustomerEmail(String customerEmail);
    Optional<TransactionEntity> findByPaymentRef(String paymentRef);

    boolean existsByCustomerEmail(String customerEmail);
}
