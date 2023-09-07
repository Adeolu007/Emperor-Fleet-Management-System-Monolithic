package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceTaskResponseDto {
    private String vehicle;
    private String description;
    private LocalDate scheduledDate;
}
