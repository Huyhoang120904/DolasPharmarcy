package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    @NotBlank(message = "Product name must not be blank")
    @Size(max = 200, message = "Product name must not exceed 200 characters")
    String productName;

    @NotBlank(message = "SKU must not be blank")
    @Size(max = 50, message = "SKU must not exceed 50 characters")
    String sku;

    @Size(max = 100, message = "Origin must not exceed 100 characters")
    String origin;

    @Size(max = 1000, message = "Warning must not exceed 1000 characters")
    String warning;

    @Size(max = 1000, message = "Ingredients must not exceed 1000 characters")
    String ingredients;

    @Size(max = 500, message = "Dosage must not exceed 500 characters")
    String dosage;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    String description;

    @Size(max = 1000, message = "Usage instruction must not exceed 1000 characters")
    String usageInstruction;


    @NotBlank(message = "Slug must not be blank")
    @Size(max = 200, message = "Slug must not exceed 200 characters")
    String slug;


    @Min(value = 0, message = "Stock must be greater than 0")
    Long stock;

    boolean requiresPrescription;

    ProductStatus productStatus;


    @Valid
    BrandRequest brand;

    @Valid
    List<ImageRequest> images;

    @Valid
    TargetRequest target;

    @Valid
    List<VariantRequest> variants;

    @Size(max = 50, message = "Supplier ID must not exceed 50 characters")
    String supplierId;

    @Size(max = 50, message = "Category ID must not exceed 50 characters")
    String categoryId;

    @Size(max = 50, message = "Promotion ID must not exceed 50 characters")
    String promotionId;
}
