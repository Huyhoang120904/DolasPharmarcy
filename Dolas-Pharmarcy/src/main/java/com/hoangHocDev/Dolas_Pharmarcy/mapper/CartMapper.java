package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CartRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CartResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toReponse(Cart cart);
}
