package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserEntityService {
    Page<UserResponse> findAll(int page, int size);
    UserResponse findMyInfo();
    UserResponse findUserById(String id);
    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(UserUpdateRequest request);
    void delete(String id);
}
