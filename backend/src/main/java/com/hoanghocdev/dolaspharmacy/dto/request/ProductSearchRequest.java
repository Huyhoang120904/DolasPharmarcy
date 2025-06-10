package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.validation.constraints.Min;
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
    String brandName;

    Long stockFrom;
    Long stockTo;

    Double discountAmountFrom;
    Double discountAmountTo;

    Double priceFrom;
    Double priceTo;
}
