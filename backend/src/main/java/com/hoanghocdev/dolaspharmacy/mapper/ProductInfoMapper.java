package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.response.ProductInfoResponse;
import com.hoanghocdev.dolaspharmacy.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProductInfoMapper {
    ProductInfoResponse toInfoResponse(Product product);
}
