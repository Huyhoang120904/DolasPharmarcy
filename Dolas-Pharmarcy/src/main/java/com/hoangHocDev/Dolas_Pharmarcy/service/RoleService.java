package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.RoleRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.RoleReponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {
    Set<RoleReponse> findAll();

    RoleReponse findRoleByName(String role);

    RoleReponse createRole(RoleRequest request);

    void delete(String roleName);
}
