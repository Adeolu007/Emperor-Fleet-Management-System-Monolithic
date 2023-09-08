package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotBlank(message = "First name is required")
    private String firstName;
  //  @NotBlank(message = "Last name is required")
    private String lastName;
    private String otherName;
    private String username;
    //@NotNull(message = "Gender is required")
    private String gender;
    //@NotNull(message = "Marital status is required")
    private String maritalStatus;
    @Email(message = "Invalid email address")
    private String email;
    private String password;
    //@NotBlank(message = "License number is required")
   // @Pattern(regexp = "[0-9]{10}", message = "National identity number must be 11 digits")
    private String licenseNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
   // @NotBlank(message = "Address is required")
    private String address;
    @Pattern(regexp = "[0-9]{11}", message = "Phone number must be 11 digits")
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<Vehicle> vehicle = new ArrayList<>();;

}
