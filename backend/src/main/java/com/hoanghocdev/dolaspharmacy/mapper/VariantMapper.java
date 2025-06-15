package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.VariantRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.VariantResponse;
import com.hoanghocdev.dolaspharmacy.entity.Variant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        ProductInfoMapper.class
})
public interface VariantMapper {
    Variant toVariant(VariantRequest request);

    VariantResponse toResponse(Variant variant);
}
