package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.response.CartResponse;
import com.hoanghocdev.dolaspharmacy.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {
    CartResponse toReponse(Cart cart);
}
