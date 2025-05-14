package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.UserEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEntityController {
    UserEntityService userEntityService;

    @GetMapping
    public ApiResponse<List<UserResponse>> findAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userEntityService.getAll())
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> myInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.getMyInfo())
                .build();
    }


    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.createUser(request))
                .build();
    }

    @PutMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userEntityService.updateUser(request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse createUser(@RequestParam String userId) {
        userEntityService.delete(userId);

        return ApiResponse.builder()
                .build();
    }


}
