package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserUpdateRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.UserResponse;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        UserDetailMapper.class
})
public interface UserEntityMapper {
    UserEntity toUserEntity(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(UserEntity userEntity);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget UserEntity userEntity, UserUpdateRequest request);
}
