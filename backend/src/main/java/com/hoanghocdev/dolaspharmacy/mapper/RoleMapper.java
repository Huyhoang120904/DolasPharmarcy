package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.RoleRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.RoleReponse;
import com.hoanghocdev.dolaspharmacy.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleReponse toRoleRepose(Role role);
}
