package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchRequest {
    String productName;
    String sku;
    String origin;
    String warning;
    String ingredients;
    String dosage;
    String description;
    String usageInstruction;
    String slug;
    Boolean requiresPrescription;
    ProductStatus productStatus;
    String supplierName;
    String targetName;
    String categoryName;

    Double discountAmountFrom;
    Double discountAmountTo;

    Double priceFrom;
    Double priceTo;
}
