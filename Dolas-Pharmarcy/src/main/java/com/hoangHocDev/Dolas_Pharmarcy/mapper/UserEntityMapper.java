package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.UserCreationRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.UserResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toUserEntity(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(UserEntity userEntity);
}
