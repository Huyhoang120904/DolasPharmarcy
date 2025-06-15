package com.hoanghocdev.dolaspharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    String id;
    double quantity;
    double finalPrice;
    VariantResponse variant;
}
