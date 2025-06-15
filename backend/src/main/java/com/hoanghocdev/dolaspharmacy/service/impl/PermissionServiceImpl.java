package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.PermissionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.PermissionResponse;
import com.hoanghocdev.dolaspharmacy.entity.Permission;
import com.hoanghocdev.dolaspharmacy.entity.Role;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.PermissionMapper;
import com.hoanghocdev.dolaspharmacy.repository.PermissionRepository;
import com.hoanghocdev.dolaspharmacy.repository.RoleRepository;
import com.hoanghocdev.dolaspharmacy.service.PermissionService;
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

        if (permissionRepository.existsById(permission.getPermissionName())) {
            throw  new AppException(ErrorCode.PERMISSION_EXISTED);
        }

        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public void delete(String id) {
        if (!permissionRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        permissionRepository.deleteById(id);
    }
}
