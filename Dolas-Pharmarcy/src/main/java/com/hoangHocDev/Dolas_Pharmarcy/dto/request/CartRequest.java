package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    String variantId;
    int quantity;
}
