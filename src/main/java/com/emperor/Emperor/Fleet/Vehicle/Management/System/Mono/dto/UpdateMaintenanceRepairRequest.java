package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMaintenanceRepairRequest {
    private long id;
    private String vehicleLicensePlate;
    private String description;
    private LocalDate scheduledDate;
    private BigDecimal cost;
}
