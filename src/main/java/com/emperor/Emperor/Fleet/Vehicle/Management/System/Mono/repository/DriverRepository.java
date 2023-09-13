package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;


import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    @Query("select d from DriverEntity d where d.username = ?1")
    Optional<DriverEntity> findDriverByUsernameOrEmail(String username);


//    Boolean existByUsername(String username);
    Boolean  existsByEmail(String email);
//    Optional<UserEntity> findByUsername(String username);
    Optional<DriverEntity> findByEmail(String email);

    @Query("select (count(d) > 0) from DriverEntity d where d.licenseNumber = ?1")
    boolean existsByLicenseNumber(String licenseNumber);

    @Query("select d from DriverEntity d where d.licenseNumber = ?1")
    Optional<DriverEntity> findByLicenseNumber(String licenseNumber);

//    @Query("select d from DriverEntity d where d.licenseNumber = ?1")
//    DriverEntity findByLicenseNumber(String licenseNumber);

//    Optional<DriverEntity> findByUsernameOrEmail(String usernameOrEmail);


   // Optional<DriverEntity> findByUsernameOrEmail(String username, String email);

    @Query("select d from DriverEntity d where d.username = ?1 or d.email = ?2")
    Optional<DriverEntity> findByTheDriverUsernameOrEmail(String username, String email);

    @Query("select (count(d) > 0) from DriverEntity d where d.username = ?1 or d.email = ?2")
    boolean existByUsernameOrEmail(String username, String email);

   // Boolean existsByUsernameOrEmail(String username, String email);




}
