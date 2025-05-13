package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PermissionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PermissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    Page<PermissionResponse> findAllPagePermission(int page, int size);
    Page<PermissionResponse> findAllPagePermissionByRole(String role, int page, int size);
    PermissionResponse createPermission(PermissionRequest request);
    void delete(String permission);
}
