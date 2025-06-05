package com.hoangHocDev.Dolas_Pharmarcy.controller;


import com.hoangHocDev.Dolas_Pharmarcy.dto.request.RoleRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.RoleReponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ApiResponse<Set<RoleReponse>> getRole(){
        return ApiResponse.<Set<RoleReponse>>builder()
                .result(roleService.findAll())
                .build();
    }

    @GetMapping("/{roleId}")
    public ApiResponse<RoleReponse> getRoleById(@PathVariable String roleId){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.findRoleByName(roleId))
                .build();
    }

    @PostMapping
    public ApiResponse<RoleReponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable String roleId){
        roleService.delete(roleId);
    }

}
