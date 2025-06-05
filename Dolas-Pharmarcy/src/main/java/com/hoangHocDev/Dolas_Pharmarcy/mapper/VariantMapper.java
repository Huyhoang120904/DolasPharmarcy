package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.VariantRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.VariantResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariantMapper {
    Variant toVariant(VariantRequest request);

    VariantResponse toReponse(Variant variant);
}
