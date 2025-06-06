package com.hoangHocDev.Dolas_Pharmarcy.dto.request;

import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ImageResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    String categoryName;
    String description;
    String slug;
    boolean isActive;
    ImageRequest image;
    String parentCategoryId;
}
