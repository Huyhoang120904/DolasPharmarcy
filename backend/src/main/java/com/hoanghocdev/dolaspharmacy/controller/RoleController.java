package com.hoanghocdev.dolaspharmacy.controller;


import com.hoanghocdev.dolaspharmacy.dto.request.RoleRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.RoleReponse;
import com.hoanghocdev.dolaspharmacy.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Role")
@Validated
public class RoleController {

    RoleService roleService;

    @GetMapping
    public ApiResponse<Set<RoleReponse>> getRole(){
        return ApiResponse.<Set<RoleReponse>>builder()
                .result(roleService.findAll())
                .build();
    }

    @GetMapping("/{roleId}")
    public ApiResponse<RoleReponse> getRoleById(@PathVariable String roleId){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.findByName(roleId))
                .build();
    }

    @PostMapping
    public ApiResponse<RoleReponse> createRole(@RequestBody @Valid RoleRequest request){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable String roleId){
        roleService.delete(roleId);
    }

}
