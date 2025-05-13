package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PermissionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PermissionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
