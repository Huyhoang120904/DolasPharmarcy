package com.hoanghocdev.dolaspharmacy.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantResponse {
    String id;
    String name;
    double price;
    int stock;
    String unit;
    Boolean isPrimary;
    ProductInfoResponse product;
}
