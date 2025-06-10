package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.BrandRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.BrandResponse;
import com.hoanghocdev.dolaspharmacy.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);
    BrandResponse toResponse(Brand brand);


}
