package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.RoleRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.RoleReponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleReponse toRoleRepose(Role role);
}
