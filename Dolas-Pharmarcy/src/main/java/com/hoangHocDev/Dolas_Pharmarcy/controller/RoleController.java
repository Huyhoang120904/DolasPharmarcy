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
    public ApiResponse<Set<RoleReponse>> getAll(){
        return ApiResponse.<Set<RoleReponse>>builder()
                .result(roleService.findAll())
                .build();
    }

    @GetMapping("/{role}")
    public ApiResponse<RoleReponse> getByRoleId(@PathVariable String role){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.findRoleByName(role))
                .build();
    }

    @PostMapping
    public ApiResponse<RoleReponse> getByRoleId(@RequestParam RoleRequest request){
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("/{role}")
    public void deleteRole(@PathVariable String role){
        roleService.delete(role);
    }

}
