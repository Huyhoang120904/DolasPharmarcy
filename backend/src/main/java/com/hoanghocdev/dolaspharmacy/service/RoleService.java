package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.RoleRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.RoleReponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {
    Set<RoleReponse> findAll();
    RoleReponse findByName(String role);
    RoleReponse createRole(RoleRequest request);
    void delete(String roleName);
}
