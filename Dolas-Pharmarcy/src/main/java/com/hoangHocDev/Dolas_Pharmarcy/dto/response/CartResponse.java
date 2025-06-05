package com.hoangHocDev.Dolas_Pharmarcy.dto.response;

import com.hoangHocDev.Dolas_Pharmarcy.entity.OrderItem;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    UserDetailResponse userDetail;
    List<OrderItemResponse> orderItems;
}
