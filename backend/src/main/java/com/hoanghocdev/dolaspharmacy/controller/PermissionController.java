package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.PermissionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.PermissionResponse;
import com.hoanghocdev.dolaspharmacy.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Permission")
@Validated
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
    public  ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @DeleteMapping("/{permissionId}")
    public void deletePermission(@PathVariable String permissionId){
        permissionService.delete(permissionId);
    }

}
