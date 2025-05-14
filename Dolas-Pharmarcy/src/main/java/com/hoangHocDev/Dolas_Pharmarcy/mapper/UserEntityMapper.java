package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserUpdateRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toUserEntity(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(UserEntity userEntity);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget UserEntity userEntity, UserUpdateRequest request);
}
