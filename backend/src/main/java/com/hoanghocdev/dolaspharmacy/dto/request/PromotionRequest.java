package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import com.hoanghocdev.dolaspharmacy.validation.ValidDateRange;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidDateRange(message = "End date must be after start date")
public class PromotionRequest {
    String promotionName;

    @NotBlank(message = "Promotion code is required")
    @Size(min = 3, max = 20, message = "Promotion code must be between 3 and 20 characters")
    String code;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    LocalDate endDate;

    @Positive(message = "Discount amount must be positive")
    @DecimalMax(value = "1", message = "Discount amount cannot exceed 100%")
    double discountAmount;

    @NotNull(message = "Promotion type is required")
    PromotionType promotionType;
}
