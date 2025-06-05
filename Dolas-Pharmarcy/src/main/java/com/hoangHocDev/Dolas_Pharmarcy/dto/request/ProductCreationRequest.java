package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import com.hoangHocDev.Dolas_Pharmarcy.entity.*;
import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    String name;
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
    List<ImageRequest> images;
    Target target;
    List<VariantRequest> variants;

    String supplierId;
    String categoryId;
    String promotionId;
}
