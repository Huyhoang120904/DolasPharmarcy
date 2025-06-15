package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    @NotBlank(message = "Variant must not be blank")
    String variantId;

    @Min(value = 1, message = "Product quantity must be equal or greater than 1")
    int quantity;
}
