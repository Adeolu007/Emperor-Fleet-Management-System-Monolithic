package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);

 //   boolean existsByPassword(String password);

    Optional<Admin> findByEmail(String email);

 //   Admin findByEmailIgnoreCase(String email);

 //   boolean existsByUserNameOrPassword(String userName, String password);

    @Query("select a from Admin a where a.licenseNumber = ?1")
    Optional<Admin> findByLicenseNumber(String licenseNumber);




    ///






}
