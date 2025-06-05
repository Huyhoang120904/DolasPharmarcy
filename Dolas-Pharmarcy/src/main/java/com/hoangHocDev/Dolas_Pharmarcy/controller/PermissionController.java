package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PermissionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PermissionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionController {
    PermissionService permissionService;

    @GetMapping
    public ApiResponse<Page<PermissionResponse>> getPermissionByPage(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size ){
        return ApiResponse.<Page<PermissionResponse>>builder()
                .result(permissionService.findAllPagePermission(page, size))
                .build();
    }

    @GetMapping("/{role}")
    public ApiResponse<Page<PermissionResponse>> getPermissionByRoleAndPage(@PathVariable String role, @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size ){
        return ApiResponse.<Page<PermissionResponse>>builder()
                .result(permissionService.findAllPagePermissionByRole( role ,page, size))
                .build();
    }

    @PostMapping
    public  ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @DeleteMapping("/{perrmisionId}")
    public void deletePermission(@PathVariable String perrmisionId){
        permissionService.delete(perrmisionId);
    }

}
