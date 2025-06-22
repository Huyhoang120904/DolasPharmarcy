package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.Gender;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.UserDetailMapper;
import com.hoanghocdev.dolaspharmacy.mapper.UserEntityMapper;
import com.hoanghocdev.dolaspharmacy.repository.*;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    UserEntityRepository userEntityRepository;
    UserEntityMapper userEntityMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserDetailRepository userDetailRepository;
    FavouritesRepository favouritesRepository;
    private final UserDetailMapper userDetailMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> pageUsers = userEntityRepository.findAll(pageable);
        return pageUsers.map(userEntityMapper::toUserResponse);
    }

    @Override
    public UserResponse findMyInfo() {
        var contextHolder = SecurityContextHolder.getContext();
        String name = contextHolder.getAuthentication().getName();

        UserEntity userEntity = userEntityRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse findUserById(String id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        UserEntity userEntity = userEntityMapper.toUserEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findById("USER").orElse(null);

        if (role == null) {
            role = roleRepository.save(Role.builder().rolename("USER").build());
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userEntity.setRoles(roles);

        userEntity = userEntityRepository.save(userEntity);

        UserDetail userDetail = userDetailMapper.toUserDetail(request.getUserDetail());
        userDetail.setUserEntity(userEntity);

        userDetail = userDetailRepository.save(userDetail);

        userEntity.setUserDetail(userDetail);
        userEntity = userEntityRepository.save(userEntity);

        Favourites favourites = Favourites.builder()
                .userDetail(userDetail)
                .products(new ArrayList<>())
                .build();

        favouritesRepository.save(favourites);

        return userEntityMapper.toUserResponse(userEntity);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userEntityRepository.findById(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        return userEntityMapper.toUserResponse(userEntityRepository.save(userEntity));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        if (!userEntityRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        userEntityRepository.deleteById(id);
    }
}
