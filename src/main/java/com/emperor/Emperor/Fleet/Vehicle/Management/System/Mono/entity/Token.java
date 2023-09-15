package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "admin_id")
    private Admin admin;

    public Token(Admin admin){
        this.admin=admin;
        createdAt = LocalDateTime.now();
        token = UUID.randomUUID().toString();
    }


}