package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRolename(String rolename);

    boolean existsByRolename(String rolename);

}
