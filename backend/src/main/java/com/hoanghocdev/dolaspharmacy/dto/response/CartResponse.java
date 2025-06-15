package com.hoanghocdev.dolaspharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String id;
    Set<OrderItemResponse> orderItems;
}
