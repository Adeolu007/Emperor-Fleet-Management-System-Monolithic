package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisitionDate;
    //@Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private DriverEntity driver;

    @Column(name = "description")
    private String description;
    @Column(name = "fuel_capacity")
    private String fuelCapacity;
    @Email
    private String email;

    private String firstName;
    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<FuelRecord> fuelRecord = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<MaintenanceRepair> maintenanceRepair= new ArrayList<>();

    @OneToMany(mappedBy = "vehicle" ,cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<OdometerReading> odometerReading = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<Reservation> reservation= new ArrayList<>();
}
