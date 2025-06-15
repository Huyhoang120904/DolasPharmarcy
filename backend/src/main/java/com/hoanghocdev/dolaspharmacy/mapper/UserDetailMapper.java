package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.UserDetailRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.UserDetailResponse;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {
    UserDetailResponse toResponse(UserDetail userDetail);
    UserDetail toUserDetail(UserDetailRequest request);
}
