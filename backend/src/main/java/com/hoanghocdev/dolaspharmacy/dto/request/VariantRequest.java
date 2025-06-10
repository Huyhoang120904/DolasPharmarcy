package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantRequest {
    @NotBlank(message = "Variant name is required")
    @Size(min = 1, max = 100, message = "Variant name must be between 1 and 100 characters")
    String name;

    @Positive(message = "Price must be positive")
    double price;

    @Min(value = 0, message = "Stock cannot be negative")
    @Max(value = 999999, message = "Stock cannot exceed 999,999")
    int stock;

    @NotBlank(message = "Unit is required")
    @Size(min = 1, max = 20, message = "Unit must be between 1 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Unit can only contain letters and spaces")
    String unit;

    @NotNull
    Boolean isPrimary;
}
