package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ImageRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String name;
    String description;
    String slug;
    boolean isActive;
    ImageRequest imageRequest;
    String parentCatergoryId;
}
