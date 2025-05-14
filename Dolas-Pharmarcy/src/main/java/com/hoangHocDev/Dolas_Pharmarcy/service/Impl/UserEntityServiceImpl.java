package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserEntity;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.UserEntityMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.RoleRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.UserEntityRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.UserEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    UserEntityRepository userEntityRepository;
    UserEntityMapper userEntityMapper;
    PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        List<UserEntity> result = userEntityRepository.findAll();
        return result.stream().map(userEntityMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getMyInfo() {
        var contextHolder = SecurityContextHolder.getContext();
        String name =  contextHolder.getAuthentication().getName();

        UserEntity userEntity = userEntityRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        UserEntity userEntity = userEntityMapper.toUserEntity(request);

        Role role = roleRepository.findById("USER").orElse(null);

        if ( role == null) {
            role = roleRepository.save(Role.builder().rolename("USER").build());
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userEntity.setRoles(roles);
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity = userEntityRepository.save(userEntity);

        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(UserUpdateRequest request) {
        UserEntity userEntity = userEntityRepository.findById(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        return userEntityMapper.toUserResponse(userEntityRepository.save(userEntity));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        userEntityRepository.deleteById(id);
    }
}
