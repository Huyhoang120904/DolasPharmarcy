package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    String variantId;
    int quantity;
}
