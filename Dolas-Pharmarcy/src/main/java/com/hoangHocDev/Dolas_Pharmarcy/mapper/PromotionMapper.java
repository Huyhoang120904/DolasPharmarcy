package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PromotionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PromotionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toPromotion(PromotionRequest request);
    PromotionResponse toPromotionResponse(Promotion promotion);

    void updatePromotion(PromotionRequest request, @MappingTarget Promotion promotion);
}
