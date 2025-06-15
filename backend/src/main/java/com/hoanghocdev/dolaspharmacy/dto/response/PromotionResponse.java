package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    String id;
    String promotionName;
    String code;
    LocalDate startDate;
    LocalDate endDate;
    double discountAmount;
    PromotionType promotionType;
}
