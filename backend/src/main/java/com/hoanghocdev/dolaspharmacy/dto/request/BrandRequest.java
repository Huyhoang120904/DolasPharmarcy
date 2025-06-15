package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {
    @NotBlank(message = "Brand name must not be blank")
    @Size(max = 1000, message = "Usage instruction must not exceed 1000 characters")
    String brandName;

    String brandOrigin;
}
