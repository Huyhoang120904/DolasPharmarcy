package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEntityService {
    List<UserResponse> getAll();
    UserResponse getMyInfo();
    UserResponse getUserById(String id);
    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(UserUpdateRequest request);
    void delete(String id);
}
