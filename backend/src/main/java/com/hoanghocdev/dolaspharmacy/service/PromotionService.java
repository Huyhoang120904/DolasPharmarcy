package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.PromotionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.PromotionResponse;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.entity.Promotion;
import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;

import java.util.Optional;

public interface PromotionService extends BaseCRUDService<PromotionResponse, PromotionRequest> {
    Optional<Promotion> getHighestAvailablePromotionForOrderByPromotionType(PromotionType promotionType);
}
