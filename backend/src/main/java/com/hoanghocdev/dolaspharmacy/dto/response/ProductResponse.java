package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String productName;
    String sku;
    String origin;
    String warning;
    String ingredients;
    String dosage;
    String description;
    String usageInstruction;
    String slug;

    boolean requiresPrescription;
    ProductStatus productStatus;
    List<ImageResponse> images;

    TargetResponse target;

    BrandResponse brand;

    PromotionResponse promotion;

    CategoryResponse category;

    SupplierResponse supplier;

    List<VariantResponse> variants;

    LocalDateTime createdAt;

}
