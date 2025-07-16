package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserEntitySearchRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public interface UserEntityService  {
    Page<UserResponse> findAll(Pageable pageable);
    UserResponse findMyInfo();
    UserResponse findUserById(String id);
    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(UserUpdateRequest request);
    void delete(String id);
    Page<UserResponse> search(UserEntitySearchRequest request, Pageable pageable);
}
