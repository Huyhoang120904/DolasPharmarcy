package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PermissionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PermissionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Permission;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.PermissionMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.PermissionRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.RoleRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;

    @Override
    public Page<PermissionResponse> findAllPagePermission(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return permissionRepository.findAll(pageable)
                .map(permissionMapper::toPermissionResponse);
    }

    @Override
    public Page<PermissionResponse> findAllPagePermissionByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Role roleEntity = roleRepository.findById(role)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return permissionRepository.findAllByRolesIs(roleEntity, pageable)
                .map(permissionMapper::toPermissionResponse);
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        if (permissionRepository.existsById(permission.getPermission())) {
            throw  new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        permission = permissionRepository.save(permission);
        PermissionResponse response = permissionMapper.toPermissionResponse(permission);
        return response;
    }

    @Override
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
