package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ImageRequest;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String categoryName;
    String description;
    String slug;
    boolean isActive;
    ImageRequest image;
    Category parentCategory;
}
