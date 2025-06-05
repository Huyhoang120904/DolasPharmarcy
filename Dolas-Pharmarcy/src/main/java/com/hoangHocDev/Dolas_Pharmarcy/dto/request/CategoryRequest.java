package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    String name;
    String description;
    String slug;
    boolean isActive;
    ImageRequest image;
    String parentCategoryId;
}
