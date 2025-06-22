package com.hoanghocdev.dolaspharmacy.dto.request;

import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    String productName;

    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "SKU can only contain uppercase letters, numbers, underscores and hyphens")
    String sku;

    @Size(max = 100, message = "Origin cannot exceed 100 characters")
    String origin;

    @Size(max = 1000, message = "Warning cannot exceed 1000 characters")
    String warning;

    @Size(max = 1000, message = "Ingredients cannot exceed 1000 characters")
    String ingredients;

    @Size(max = 500, message = "Dosage cannot exceed 500 characters")
    String dosage;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    String description;

    @Size(max = 1000, message = "Usage instruction cannot exceed 1000 characters")
    String usageInstruction;

    boolean requiresPrescription;

    @NotNull(message = "Product status is required")
    ProductStatus productStatus;

    @Valid
    @NotEmpty(message = "At least one image is required")
    List<ImageRequest> images;

    @Valid
    BrandRequest brand;

    @Valid
    TargetRequest target;

    @Valid
    @NotEmpty(message = "At least one variant is required")
    List<VariantRequest> variants;

    @NotBlank(message = "Supplier ID is required")
    String supplierId;

    @NotBlank(message = "Category ID is required")
    String categoryId;

    @NotBlank(message = "Slug is required")
    String slug;

    @Valid
    PromotionRequest promotion;
}
