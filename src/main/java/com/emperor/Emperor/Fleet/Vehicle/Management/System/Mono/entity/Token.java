package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "confirmation_token")
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
    private Date createdAt;

    @OneToOne(targetEntity = Organizer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "organizer_id")
    private Organizer organizer;

    public ConfirmationToken(Organizer organizer){
        this.organizer=organizer;
        createdAt = new Date();
        token = UUID.randomUUID().toString();
    }


}