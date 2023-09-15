package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.serviceImpl;

import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleRequest;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.dto.RoleResponse;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.entity.RoleEntity;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.exception.CustomException;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.repository.RoleRepository;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.service.RoleService;
import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.utils.ResponseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleResponse addRole(RoleRequest request) {
        boolean isExists = roleRepository.existsByRolename(request.getRoleName());
        if(isExists){
            throw new CustomException(ResponseUtils.ROLE_EXISTS_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        RoleEntity roles = RoleEntity.builder()
                .rolename(request.getRoleName())
                .build();
        roleRepository.save(roles);
        return RoleResponse.builder()
                .id(roles.getId())
                .roleName(roles.getRolename())
                .build();
    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity role =roleRepository.findById(id)
                .orElseThrow(()-> new CustomException(ResponseUtils.ROLE_NOT_FOUND_MESSAGE,HttpStatus.BAD_REQUEST));
        roleRepository.delete(role);
    }

}
