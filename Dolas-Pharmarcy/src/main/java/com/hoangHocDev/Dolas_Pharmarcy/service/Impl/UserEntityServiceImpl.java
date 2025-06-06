package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.*;
import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.Gender;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.UserEntityMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.*;
import com.hoangHocDev.Dolas_Pharmarcy.service.UserEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    UserEntityRepository userEntityRepository;
    UserEntityMapper userEntityMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserDetailRepository userDetailRepository;
    CartRepository cartRepository;
    FavouritesRepository favouritesRepository;

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

        UserEntity userEntity = userEntityRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
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

        userEntity.setCreatedAt(LocalDateTime.now());

        userEntity = userEntityRepository.save(userEntity);

        String fullName = request.getFullName();
        LocalDate dob = request.getDob();
        Gender gender = Gender.valueOf(request.getGender());
        UserDetail userDetail = UserDetail.builder()
                .userEntity(userEntity)
                .fullName(fullName)
                .gender(gender)
                .dob(dob)
                .build();

        userDetail = userDetailRepository.save(userDetail);

        Cart cart = Cart.builder()
                .userDetail(userDetail)
                .orderItems(new ArrayList<>())
                .build();

        cartRepository.save(cart);

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
