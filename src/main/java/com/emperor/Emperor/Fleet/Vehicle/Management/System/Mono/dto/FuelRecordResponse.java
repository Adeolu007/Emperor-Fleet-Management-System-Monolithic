package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelRecordResponse {
    private String licensePlate;
    private LocalDate refuelingDate;
    private double litersFilled;
    private double costPerLiter;
    private double totalCost;}
