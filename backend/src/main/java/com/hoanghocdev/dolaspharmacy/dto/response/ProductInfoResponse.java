package com.hoanghocdev.dolaspharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductInfoResponse {
    String id;
    String productName;
    PromotionResponse promotion;
    List<ImageResponse> images;
}
