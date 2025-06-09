package com.hoanghocdev.dolaspharmacy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    String categoryName;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    String description;

    @NotBlank(message = "Slug must not be blank")
    @Size(max = 100, message = "Slug must not exceed 100 characters")
    String slug;

    boolean isActive;

    @NotNull(message = "Image must not be null")
    ImageRequest image;

    @Size(max = 50, message = "Parent category ID must not exceed 50 characters")
    String parentCategoryId;
}