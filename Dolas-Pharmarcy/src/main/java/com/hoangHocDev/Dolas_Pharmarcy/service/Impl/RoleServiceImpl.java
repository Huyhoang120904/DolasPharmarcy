package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.RoleRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.RoleReponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Permission;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.RoleMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.PermissionRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.RoleRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.RoleService;
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
        Set<RoleReponse> reponses = new HashSet<>(roleRepository.findAll()
                .stream().map(roleMapper::toRoleRepose)
                .toList());
        return reponses;
    }

    @Override
    public RoleReponse findByName(String role) {
        if (!roleRepository.existsById(role)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        Role role1 = roleRepository.findById(role).get();
        RoleReponse reponse = roleMapper.toRoleRepose(role1);

        return reponse;
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
        RoleReponse reponse = roleMapper.toRoleRepose(role);

        return reponse;
    }

    @Override
    public void delete(String roleName) {
        if (!roleRepository.existsById(roleName)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        roleRepository.deleteById(roleName);
    }
}
