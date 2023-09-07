package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email_service")
public class EmailEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

//    private Long id;
    private String message;
    private String subject;
    private String attachment;
    @CreatedDate
    private LocalDateTime created;
}
