package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceTaskResponseDto {
    private String vehicleLicensePlate;
    private String description;
    private BigDecimal cost;
    private LocalDate scheduledDate;
}
