package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.PromotionType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    String promotionName;
    String code;

    LocalDate startDate;
    LocalDate endDate;

    double discountAmount;

    @Enumerated
    PromotionType promotionType;
}
