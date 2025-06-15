package com.hoanghocdev.dolaspharmacy.dto.response;

import com.hoanghocdev.dolaspharmacy.dto.request.ImageRequest;
import com.hoanghocdev.dolaspharmacy.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String id;
    String categoryName;
    String description;
    String slug;
    boolean isActive;
    ImageRequest image;
    Category parentCategory;
}
