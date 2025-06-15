package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchRequest {
    String productName;
    String sku;
    String warning;
    String ingredients;
    String dosage;
    String description;
    String usageInstruction;
    String slug;
    String categoryName;

    Boolean requiresPrescription;
    ProductStatus productStatus;

    List<String> origin;
    List<String> supplierName;
    List<String>  targetName;
    List<String> brandName;

    Long stockFrom;
    Long stockTo;

    Double discountAmountFrom;
    Double discountAmountTo;

    Double priceFrom;
    Double priceTo;
}
