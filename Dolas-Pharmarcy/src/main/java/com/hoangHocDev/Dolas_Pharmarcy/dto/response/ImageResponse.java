package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    String url;
    String alt;
    boolean isPrimary;
}
