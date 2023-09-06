package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "Licence_plate")
    private String licensePlate;

    @Column(name = "year")
    private int year;
//
//    @Column(name = "registration_number")
//    private String registrationNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisitionDate;

    @ManyToOne
    @JoinColumn(name = "driver_id") // Establish a Many-to-One relationship with drivers
    private DriverEntity driver;

    @Column(name = "description")
    private String description;
    @Column(name = "fuel_capacity")
    private String fuelCapacity;
    @Email
    private String email;

    private String firstName;
}
