package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.RoleRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.RoleReponse;
import com.hoanghocdev.dolaspharmacy.entity.Permission;
import com.hoanghocdev.dolaspharmacy.entity.Role;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.RoleMapper;
import com.hoanghocdev.dolaspharmacy.repository.PermissionRepository;
import com.hoanghocdev.dolaspharmacy.repository.RoleRepository;
import com.hoanghocdev.dolaspharmacy.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public Set<RoleReponse> findAll() {
        return new HashSet<>(roleRepository.findAll()
                .stream().map(roleMapper::toRoleRepose)
                .toList());
    }

    @Override
    public RoleReponse findByName(String role) {
        if (!roleRepository.existsById(role)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        Role role1 = roleRepository.findById(role)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return roleMapper.toRoleRepose(role1);
    }

    @Override
    public RoleReponse createRole(RoleRequest request) {

        Role role = roleMapper.toRole(request);

        if (roleRepository.existsById(request.getRolename())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        if (!CollectionUtils.isEmpty(request.getPermissions())) {
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions())) ;
            role.setPermissions(permissions);
        }

        role = roleRepository.save(role);

        return roleMapper.toRoleRepose(role);
    }

    @Override
    public void delete(String roleName) {
        if (!roleRepository.existsById(roleName)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        roleRepository.deleteById(roleName);
    }
}
