package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRepairRequest {
    private String vehicleLicensePlate;
    private String description;
    private LocalDate scheduledDate;
    private BigDecimal cost;
}
