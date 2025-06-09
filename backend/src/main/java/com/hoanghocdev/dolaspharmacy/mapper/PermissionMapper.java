package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.PermissionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.PermissionResponse;
import com.hoanghocdev.dolaspharmacy.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
